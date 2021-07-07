package com.example.chat.common

import java.text.SimpleDateFormat
import java.util.*

object Constant {

    const val PROF_USERNAME ="user"
    const val PROF_USER_ID ="id"
    const val PROF_EMAIL ="email"

    val time:String
    get() = SimpleDateFormat("dd MMM yyyy , HH.mm" , Locale.getDefault()).format(Date())
}