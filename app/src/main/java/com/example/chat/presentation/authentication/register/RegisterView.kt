package com.example.chat.presentation.authentication.register

import com.example.chat.data.entity.User

interface RegisterView {

    fun onUsernameEmpty()
    fun onEmailEmpty()
    fun onEmailInvalid()
    fun onPasswordEmpty()
    fun onPasswordToShort()
    fun onConfirmPasswordEmpty()
    fun onConfirmPasswordNotMatch()
    fun onRegisterStart()
    fun onProgress(visibility:Int)
    fun onRegisterSuccess(user: User)
    fun onRegisterFailed(error:String?)
}