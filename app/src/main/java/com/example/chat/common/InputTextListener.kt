package com.example.chat.common

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class InputTextListener(private val input: TextInputLayout) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        input.error = ""
        input.isErrorEnabled = false
    }

    override fun afterTextChanged(s: Editable?) {

    }
}