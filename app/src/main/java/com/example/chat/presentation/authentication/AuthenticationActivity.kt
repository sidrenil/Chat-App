package com.example.chat.presentation.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.R
import com.example.chat.data.entity.User
import com.example.chat.preferences.ChatPrefences
import com.example.chat.presentation.authentication.login.LoginFragment
import com.example.chat.presentation.authentication.register.RegisterFragment
import com.example.chat.presentation.chatroom.ChatRoomActivity
import com.example.chat.replaceFragment

class AuthenticationActivity : AppCompatActivity(), AuthenticationPageListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            replaceFragment(R.id.containerAuth, LoginFragment())

        }
    }

    override fun onLoginPage() = replaceFragment(R.id.containerAuth, LoginFragment())
    override fun onRegisterPage() = replaceFragment(R.id.containerAuth, RegisterFragment())
    override fun onAuthenticateSuccess(user: User) {
        ChatPrefences.initPreferences(this).userInfo = user
        startActivity(Intent(this@AuthenticationActivity, ChatRoomActivity::class.java))
        finish()
    }
}