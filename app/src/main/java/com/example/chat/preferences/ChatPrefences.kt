package com.example.chat.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.chat.common.Constant
import com.example.chat.data.entity.User


class ChatPrefences {
    var userInfo : User
        get() {
         val user = User()
            user.username = preferences.getString(Constant.PROF_USERNAME, "")
            user.email = preferences.getString(Constant.PROF_EMAIL, "")
            user.userId = preferences.getString(Constant.PROF_USER_ID , "")
            return user
        }
        set(modelUser) {
            val editor = preferences.edit()
            editor.putString(Constant.PROF_EMAIL,modelUser.email)
            editor.putString(Constant.PROF_USERNAME,modelUser.username)
            editor.putString(Constant.PROF_USER_ID,modelUser.userId)
            editor.apply()
        }
    companion object {

        private lateinit var preferences: SharedPreferences

        fun initPreferences(context: Context): ChatPrefences {
            preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
            return ChatPrefences()
        }
    }
}
