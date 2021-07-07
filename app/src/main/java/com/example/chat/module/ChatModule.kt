package com.example.chat.module

import com.example.chat.data.repository.authentication.AuthenticationRepository
import com.example.chat.data.repository.database.MessageRepository
import com.example.chat.data.route.ChatReferences
import com.example.chat.presentation.authentication.login.LoginPresenter
import com.example.chat.presentation.authentication.register.RegisterPresenter
import com.example.chat.presentation.chatroom.ChatRoomPresenter
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module


        val chatModule = module {

        single { ChatReferences() }
        single { FirebaseAuth.getInstance() }

        factory { AuthenticationRepository(get(), get()) }
        factory { MessageRepository(get()) }

        factory { LoginPresenter(get()) }
        factory { RegisterPresenter(get()) }
        factory { ChatRoomPresenter(get()) }

    }
