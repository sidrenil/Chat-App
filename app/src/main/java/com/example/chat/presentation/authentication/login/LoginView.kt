package com.example.chat.presentation.authentication.login

import com.example.chat.data.entity.User

interface LoginView {
    fun onEmailEmpty()
    fun onEmailInvalid()
    fun onPasswordEmpty()
    fun onLoginStart()
    fun onProgress(visibility: Int)
    fun onLoginSuccess(user: User)
    fun onLoginFailed(error:String?)
}