package com.example.chat.data.repository.database

import com.example.chat.data.entity.Chat

interface MessageRepositoryCallback {

    fun onMessageComing(chat: Chat)
    fun onMessageUpdate(position: Int, chat: Chat)
    fun onMessageDeleted(position: Int)
}