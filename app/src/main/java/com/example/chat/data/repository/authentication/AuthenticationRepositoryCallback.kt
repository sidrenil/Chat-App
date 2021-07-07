package com.example.chat.data.repository.authentication

interface AuthenticationRepositoryCallback {
    fun onSuccess(user: com.example.chat.data.entity.User)
    fun onFailed(error: String?)
}