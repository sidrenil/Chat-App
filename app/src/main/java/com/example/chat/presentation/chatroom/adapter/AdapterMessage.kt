package com.example.chat.presentation.chatroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.data.entity.Chat
import com.example.chat.preferences.ChatPrefences

class AdapterMessage(context: Context, private val listChat: List<Chat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        const val FROM = 1
        const val TO = 2
    }

    val user = ChatPrefences.initPreferences(context).userInfo.username
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FROM -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.from_message, parent, false)
                HolderMessageFrom(v)
            }
            TO -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.to_message, parent, false)
                HolderMessageTo(v)

            }
            else -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.from_message, parent, false)
                HolderMessageFrom(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HolderMessageFrom -> holder.bindChatContent(listChat[position])
            is HolderMessageTo -> holder.bindChatContent(listChat[position])
        }
    }


    override fun getItemCount(): Int {
        return listChat.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listChat[position].user == user) TO else FROM
    }

}