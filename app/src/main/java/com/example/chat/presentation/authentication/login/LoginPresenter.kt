package com.example.chat.presentation.authentication.login

import android.view.View
import com.example.chat.data.entity.User
import com.example.chat.data.repository.authentication.AuthenticationRepository
import com.example.chat.data.repository.authentication.AuthenticationRepositoryCallback
import com.example.chat.isEmailValid

class LoginPresenter(private val authenticationRepository: AuthenticationRepository) {


    private var view: LoginView? = null

    fun attachView(view: LoginView){
          this.view = view
    }

    fun detachView(){

        this.view = null
    }

    fun doLogin(user: User){

        if (user.email?.isEmpty() == true){

            view?.onEmailEmpty()
            return
        }

        if (user.email?.isEmailValid()== false){
            view?.onEmailInvalid()
            return
        }
        if (user.password?.isEmpty() == true){
            view?.onPasswordEmpty()
            return
        }
        view?.onLoginStart()
        view?.onProgress(View.GONE)

        authenticationRepository.doLogin(user , object :AuthenticationRepositoryCallback {

            override fun onSuccess(user: com.example.chat.data.entity.User) {
                view?.onLoginSuccess(user)
                view?.onProgress(View.VISIBLE)
            }

            override fun onFailed(error: String?) {
                view?.onLoginFailed(error)
                view?.onProgress(View.VISIBLE)
            }
            
        })
    }
}