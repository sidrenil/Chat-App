package com.example.chat.data.route

import com.google.firebase.database.FirebaseDatabase

class ChatReferences {

    fun chatReferences()= FirebaseDatabase.getInstance().getReference("chat")
    fun userReferances()= FirebaseDatabase.getInstance().getReference("user")
}