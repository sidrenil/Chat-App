package com.example.chat.presentation.chatroom.adapter

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.data.entity.Chat
import kotlinx.android.extensions.LayoutContainer

class HolderMessageTo(override val containerView: View?) :
    RecyclerView.ViewHolder(containerView as View), LayoutContainer {

    fun bindChatContent(chat: Chat) {
        val usernameGroup = containerView?.findViewById<LinearLayout>(R.id.toUsernameGroup)
        val userName = containerView?.findViewById<TextView>(R.id.fromUsername)
        val fromMessage = containerView?.findViewById<TextView>(R.id.fromMessage)



        if (chat.isSameUser == true) {
            usernameGroup?.visibility = View.GONE

        } else {
            usernameGroup?.visibility = View.VISIBLE
        }
        userName?.text = chat.user
        fromMessage?.text = chat.message
    }
}