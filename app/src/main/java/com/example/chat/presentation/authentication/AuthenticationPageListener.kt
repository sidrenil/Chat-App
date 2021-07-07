package com.example.chat.presentation.authentication

import com.example.chat.data.entity.User

interface AuthenticationPageListener {
    fun onLoginPage()
    fun onRegisterPage()
    fun onAuthenticateSuccess(user: User)

}