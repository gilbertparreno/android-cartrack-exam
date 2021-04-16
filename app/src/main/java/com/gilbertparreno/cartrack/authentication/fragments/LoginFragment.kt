package com.gilbertparreno.cartrack.authentication.fragments

import android.content.Context
import android.os.Bundle
import com.gilbertparreno.cartrack.CartrackApplication
import com.gilbertparreno.cartrack.authentication.viewModels.LoginViewModel
import com.gilbertparreno.cartrack.authentication.views.LoginView
import com.gilbertparreno.cartrack.authentication.views.LoginViewDelegate
import com.gilbertparreno.cartrack.core.base.BaseFragment

class LoginFragment : BaseFragment<LoginViewModel, LoginView>(), LoginViewDelegate {

    override fun inject() {
        CartrackApplication.appComponent.inject(this)
    }

    override fun onCreateView(context: Context, savedInstanceState: Bundle?) =
        LoginView(context).also {
            it.delegate = this
        }

    override fun observerChanges() {

    }

    override fun onViewCreated(contentView: LoginView, savedInstanceState: Bundle?) {
        viewModel.login("user1@gmail.com", "user1P@ssword")
    }
}