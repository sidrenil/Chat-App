package com.example.chat.presentation.authentication.register

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.chat.R
import com.example.chat.common.InputTextListener
import com.example.chat.data.entity.User
import com.example.chat.hideSoftInput
import com.example.chat.presentation.authentication.AuthenticationPageListener
import com.example.chat.presentation.chatroom.ChatRoomActivity
import com.example.chat.showSnackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_login.inputEmail
import kotlinx.android.synthetic.main.fragment_login.inputPass
import kotlinx.android.synthetic.main.fragment_login.loading
import kotlinx.android.synthetic.main.fragment_login.rootView
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.ext.android.inject
import java.util.*

class RegisterFragment : Fragment(), RegisterView {

    private lateinit var pageListener: AuthenticationPageListener
    private val registerPresenter by inject<RegisterPresenter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerPresenter.attachView(this)

        regisUser.addTextChangedListener(InputTextListener(inputUsername))
        regisEmail.addTextChangedListener(InputTextListener(inputEmail))
        regisPass.addTextChangedListener(InputTextListener(inputPass))
        regisPassConfirm.addTextChangedListener(InputTextListener(inputPassConfirm))



        registerButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


        txtSignIn.setOnClickListener { pageListener.onLoginPage() }
        btnSignUp.setOnClickListener {
            val user = User(
                username = regisUser.text.toString().trim(),
                email = regisEmail.text.toString().trim(),
                password = regisPass.text.toString().trim(),
                confirmPassword = regisPassConfirm.text.toString().trim(),
            )
            registerPresenter.doRegister(user)
            uploadImage()

        }

    }
    
    


    var photoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            photoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,photoUri)
            registerButton.visibility = GONE
            //Picasso.get().load(photoUri).into(registerImage)
            Glide.with(requireContext()).load(bitmap).circleCrop().into(registerImage)
            newImage.alpha = 0f
        }
    }

    private fun uploadImage() {

        if (photoUri == null)
            return
        val fileName = UUID.randomUUID().toString()
        val referances = FirebaseStorage.getInstance().getReference("/images/$fileName")
        referances.putFile(photoUri!!)
            .addOnSuccessListener {
                Log.d("register", "resim yüklendi:${it?.metadata?.path}")
                referances.downloadUrl.addOnSuccessListener {
                    savedUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
            }
    }

    private fun savedUserToFirebaseDatabase(profilImageUrl: String) {

        val uid = FirebaseAuth.getInstance().uid ?: ""
        val referances = FirebaseDatabase.getInstance().reference.child("users/$uid")
        val user = User(uid, profilImageUrl, regisUser.text.toString())
        referances.setValue(user).addOnCompleteListener {
            if (it.isSuccessful) {

                val intent = Intent(context, ChatRoomActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        pageListener = context as AuthenticationPageListener
    }


    override fun onUsernameEmpty() {

        inputUsername.error = "kullanıcı adını giriniz"
        regisUser.requestFocus()
    }

    override fun onEmailEmpty() {
        inputEmail.error = "email giriniz"
        regisEmail.requestFocus()
    }

    override fun onEmailInvalid() {
        inputEmail.error = "yanlış email"
        regisEmail.requestFocus()
    }

    override fun onPasswordEmpty() {
        inputPass.error = "şifre giriniz"
        regisPass.requestFocus()
    }

    override fun onPasswordToShort() {
        inputPass.error = "şifre kısa"
        regisPass.requestFocus()
    }

    override fun onConfirmPasswordEmpty() {
        inputPassConfirm.error = "şifreyi tekrar girin"
        regisPassConfirm.requestFocus()
    }

    override fun onConfirmPasswordNotMatch() {
        inputPassConfirm.error = "şifre yanlış"
        regisPassConfirm.requestFocus()
    }

    override fun onRegisterStart() {
        context?.hideSoftInput(regisEmail)

        btnSignUp.isEnabled = false
        setInputTextEnabled(false)
    }

    override fun onProgress(visibility: Int) {
        loading.visibility = visibility

    }

    override fun onRegisterSuccess(user: User) {
        pageListener.onAuthenticateSuccess(user)
    }

    override fun onRegisterFailed(error: String?) {
        rootView.showSnackbar(error)
        btnSignUp.isEnabled = true
        setInputTextEnabled(true)
    }

    private fun setInputTextEnabled(state: Boolean) {
        inputUsername.isEnabled = state
        inputEmail.isEnabled = state
        inputPass.isEnabled = state
        inputPassConfirm.isEnabled = state
    }

    override fun onDetach() {
        super.onDetach()
        registerPresenter.detachView()
    }
}