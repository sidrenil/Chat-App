package com.example.chat.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.R
import com.example.chat.presentation.authentication.AuthenticationActivity
import com.example.chat.presentation.chatroom.ChatRoomActivity
import com.google.firebase.auth.FirebaseAuth

class LauncherActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val auth=FirebaseAuth.getInstance()
        if (auth.currentUser != null){
            startActivity(Intent(this, ChatRoomActivity::class.java))
        }
        else{
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }
        finish()
    }
}