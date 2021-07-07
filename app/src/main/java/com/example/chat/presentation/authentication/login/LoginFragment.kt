package com.example.chat.presentation.authentication.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chat.R
import com.example.chat.common.InputTextListener
import com.example.chat.data.entity.User
import com.example.chat.hideSoftInput
import com.example.chat.presentation.authentication.AuthenticationPageListener
import com.example.chat.showSnackbar
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject

class LoginFragment : Fragment(), LoginView {
    private lateinit var pageListener: AuthenticationPageListener
    private val loginPresenter by inject<LoginPresenter>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginPresenter.attachView(this)

        loginEmail.addTextChangedListener(InputTextListener(inputEmail))
        loginPass.addTextChangedListener(InputTextListener(inputPass))

        txtSignUp.setOnClickListener { pageListener.onRegisterPage() }
        btnSignIn.setOnClickListener {

            val user = User(
                email = loginEmail.text.toString().trim(),
                        password = loginPass . text . toString().trim())
                        loginPresenter . doLogin (user)
        }
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        pageListener = context as AuthenticationPageListener
    }


    override fun onEmailEmpty() {
        inputEmail.error = "email girin"
        loginEmail.requestFocus()
    }

    override fun onEmailInvalid() {
        inputEmail.error = "hatalı email"
        loginPass.requestFocus()
    }

    override fun onPasswordEmpty() {
        inputPass.error = "şifre girin"
        loginPass.requestFocus()
    }

    override fun onLoginStart() {
        context?.hideSoftInput(loginEmail)

        btnSignIn.isEnabled = false
        setInputTextEnabled(false)
    }

    override fun onProgress(visibility: Int) {
        loading.visibility = visibility

    }

    override fun onLoginSuccess(user: User) {
        pageListener.onAuthenticateSuccess(user)
    }

    override fun onLoginFailed(error: String?) {

        rootView.showSnackbar(error)
        btnSignIn.isEnabled = true
        setInputTextEnabled(true)

    }

    private fun setInputTextEnabled(state: Boolean) {
        inputEmail.isEnabled = state
        inputPass.isEnabled = state
    }

    override fun onDetach() {
        super.onDetach()
        loginPresenter.detachView()
    }
}