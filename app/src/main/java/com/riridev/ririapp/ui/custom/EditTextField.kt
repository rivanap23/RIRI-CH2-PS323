package com.riridev.ririapp.ui.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

class EditTextField: TextInputEditText {
        constructor(context: Context) : super(context) {
            init()
        }

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
            init()
        }

        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr,
        ) {
            init()
        }

        private fun init()  {
            addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                        // do nothing
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                        // do nothing
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val inputEmail = s.toString()
                        if (isEmailValid(inputEmail))
                        {
                            error = null
                        } else {
                            setError("Email is not valid", null)
                        }
                    }
                },
            )
        }

        private fun isEmailValid(email: String): Boolean  {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
}