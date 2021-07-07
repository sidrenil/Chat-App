package com.example.chat.presentation.chatroom

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.common.Constant
import com.example.chat.common.EditTextListener
import com.example.chat.data.entity.Chat
import com.example.chat.preferences.ChatPrefences
import com.example.chat.presentation.LauncherActivity
import com.example.chat.presentation.chatroom.adapter.AdapterMessage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_chat_room.*
import org.koin.android.ext.android.inject
import java.util.*

class ChatRoomActivity:AppCompatActivity(),ChatRoomView {
    private lateinit var adapterMessage: AdapterMessage
    private val chatRoomPresenter by inject<ChatRoomPresenter>()
    private var listChat:MutableList<Chat> =ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val user = ChatPrefences.initPreferences(this).userInfo
        mainToolbar.title ="EDE-CHAT"

        setSupportActionBar(mainToolbar)
        etMessage.addTextChangedListener(EditTextListener(btnSend))

        adapterMessage = AdapterMessage(this, listChat)

        with(chat){
            hasFixedSize()
            layoutManager = LinearLayoutManager(context).apply{stackFromEnd = true}
            adapter = adapterMessage
        }

        btnSend.setOnClickListener{
            val username = user.username
            val message = etMessage?.text.toString()
            val time = Constant.time

            val chat = Chat()
            chat.user = username
            chat.message = message
            chat.time = time

            chatRoomPresenter.sendMessage(chat)
            etMessage?.setText("")
        }
            chatRoomPresenter.attachView(this)
            chatRoomPresenter.getMessages()
    }

    override fun onMessageComing(chat: Chat) {
        listChat.add(chat)
        adapterMessage.notifyItemInserted(listChat.lastIndex)
    }

    override fun onMessageUpdate(position: Int, chat: Chat) {
        listChat[position] = chat
        adapterMessage.notifyItemChanged(position,chat)
    }

    override fun onMessageDeleted(position: Int) {
        listChat.removeAt(position)
        adapterMessage.notifyItemRemoved(position)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        chatRoomPresenter.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId ?: 0 == R.id.menu_logout){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("çıkış")
            builder.setMessage("emin misin?")
            builder.setPositiveButton("Evet") {_,_ ->
                val auth = FirebaseAuth.getInstance()
                auth.signOut()

                startActivity(Intent(this, LauncherActivity::class.java))
                finish()
            }
            builder.setNegativeButton("Hayır" , null)
            builder.create().show()
        }
        return super.onOptionsItemSelected(item)
    }

}
