package com.gilbertparreno.cartrack.authentication.views

import android.content.Context
import android.util.Patterns
import androidx.core.widget.addTextChangedListener
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseFragmentView
import com.gilbertparreno.cartrack.core.extensions.setDebounceClickListener
import kotlinx.android.synthetic.main.view_app_bar.view.*
import kotlinx.android.synthetic.main.view_login.view.*

interface LoginViewDelegate {
    fun authenticate(email: String, password: String)
}

class LoginView(context: Context) : BaseFragmentView(context) {

    var delegate: LoginViewDelegate? = null

    init {
        inflate(context, R.layout.view_login, this)

        toolbar.setTitle(R.string.window_login_title)

        continueButton.setDebounceClickListener {
            delegate?.authenticate(emailText.text.toString(), passwordText.text.toString())
        }
        emailText.addTextChangedListener {
            continueButton.isEnabled = isFieldsValid()
        }
        passwordText.addTextChangedListener {
            continueButton.isEnabled = isFieldsValid()
        }
    }

    private fun isFieldsValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(emailText.text.toString())
            .matches() && !passwordText.text.toString().isEmpty()
    }
}