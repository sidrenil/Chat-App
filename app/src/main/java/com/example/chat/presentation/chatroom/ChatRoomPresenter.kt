package com.example.chat.presentation.chatroom

import com.example.chat.data.entity.Chat
import com.example.chat.data.repository.database.MessageRepository
import com.example.chat.data.repository.database.MessageRepositoryCallback

class ChatRoomPresenter (private val messageRepository: MessageRepository){

    private var view:ChatRoomView? = null
    private var chatPosition = 0
    private val messages = mutableListOf<Chat>()


    fun attachView(view: ChatRoomView) {
        this.view = view
    }
    fun detachView() {
        this.view = null
    }

    fun getMessages(){
        messageRepository.getMessage(object : MessageRepositoryCallback{
            override fun onMessageComing(chat: Chat) {
                if (chatPosition == 0){
                    view?.onMessageComing(chat.copy(
                        isSameUser = false
                    ))
                }
                else{
                    val before= messages[chatPosition -1].user
                    view?.onMessageComing(chat.copy(
                        isSameUser = before == chat.user
                    ))
                }
                messages.add(chat)
                chatPosition++
            }

            override fun onMessageUpdate(position: Int, chat: Chat) {
                view?.onMessageUpdate(position,chat)
            }

            override fun onMessageDeleted(position: Int) {
                view?.onMessageDeleted(position)
            }
        })
    }

    fun sendMessage(chat: Chat){
        messageRepository.sendMessage(chat)
    }
}