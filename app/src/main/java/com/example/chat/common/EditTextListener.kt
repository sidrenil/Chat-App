package com.example.chat.common

import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class EditTextListener(private val btn: Button) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        TransitionManager.beginDelayedTransition(btn.rootView as ViewGroup)
        if (charSequence.isEmpty()) {
            btn.visibility = View.INVISIBLE
        } else {
            btn.visibility = View.VISIBLE
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }
}