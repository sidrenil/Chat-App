package com.example.chat.data.repository.database

import com.example.chat.data.entity.Chat
import com.example.chat.data.route.ChatReferences
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class MessageRepository(private val chatReferences: ChatReferences) {

    private val messageId = mutableListOf<String>()

    fun getMessage(messageRepositoryCallback: MessageRepositoryCallback) {
        chatReferences.chatReferences().addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chat = snapshot.getValue(Chat::class.java) as Chat
                messageId.add(chat.messageId ?: "0")
                messageRepositoryCallback.onMessageComing(chat)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chat = snapshot.getValue(Chat::class.java) as Chat
                val position = messageId.indexOf(chat.messageId)
                messageRepositoryCallback.onMessageUpdate(position, chat)

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val chat = snapshot.getValue(Chat::class.java) as Chat
                val position = messageId.indexOf(chat.messageId)
                messageId.removeAt(position)
                messageRepositoryCallback.onMessageDeleted(position)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun sendMessage(chat: Chat){

        val messageID =chatReferences.chatReferences().push().key
        chatReferences.chatReferences().child(messageID ?: "0")
            .setValue(chat.copy(messageId =  messageID))
    }
}