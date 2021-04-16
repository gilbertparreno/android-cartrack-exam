package com.gilbertparreno.cartrack.authentication.views

import android.content.Context
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.core.base.BaseFragmentView

interface LoginViewDelegate

class LoginView(context: Context) : BaseFragmentView(context) {

    var delegate: LoginViewDelegate? = null

    init {
        inflate(context, R.layout.view_login, this)
    }
}