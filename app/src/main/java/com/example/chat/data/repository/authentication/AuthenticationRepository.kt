package com.example.chat.data.repository.authentication

import com.example.chat.data.entity.User
import com.example.chat.data.route.ChatReferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AuthenticationRepository(
    private val authentication: FirebaseAuth,
    private val chatReferences: ChatReferences
) {

    fun doRegister(user: User, listener: AuthenticationRepositoryCallback) {

        authentication.createUserWithEmailAndPassword(
            user.email as String,
            user.confirmPassword as String
        )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val userId = it.result?.user?.uid
                    val newUser = user.copy(
                        userId = userId,
                        password = null,
                        confirmPassword = null
                    )

                    chatReferences.userReferances().child(userId ?: "0")
                        .setValue(newUser)
                    listener.onSuccess(newUser)
                } else {
                    listener.onFailed(it.exception?.message)
                }
            }
    }

    fun doLogin(user: User, listener: AuthenticationRepositoryCallback) {

        authentication
            .signInWithEmailAndPassword(user.email as String, user.password as String)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    val userId = it.result?.user?.uid

                    chatReferences.userReferances().child(userId ?: "0")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {

                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                val newUser = snapshot.getValue(User::class.java) as User
                                listener.onSuccess(newUser)
                            }
                        })
                } else {

                    listener.onFailed(it.exception?.message)
                }
            }
    }
}