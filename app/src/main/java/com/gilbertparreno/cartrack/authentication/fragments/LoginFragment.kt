package com.gilbertparreno.cartrack.authentication.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.gilbertparreno.cartrack.CartrackApplication
import com.gilbertparreno.cartrack.R
import com.gilbertparreno.cartrack.authentication.viewModels.LoginViewModel
import com.gilbertparreno.cartrack.authentication.views.LoginView
import com.gilbertparreno.cartrack.authentication.views.LoginViewDelegate
import com.gilbertparreno.cartrack.core.base.BaseFragment
import com.gilbertparreno.cartrack.core.extensions.showErrorSnackbar
import com.gilbertparreno.cartrack.core.networking.taskStatus.TaskStatus
import com.gilbertparreno.cartrack.core.sharedPreferences.UserSharedPreferences
import com.gilbertparreno.cartrack.main.activities.MainActivity
import javax.inject.Inject

class LoginFragment : BaseFragment<LoginViewModel, LoginView>(), LoginViewDelegate {

    @Inject lateinit var userPreferences: UserSharedPreferences

    override fun inject() {
        CartrackApplication.appComponent.inject(this)
    }

    override fun onCreateView(context: Context, savedInstanceState: Bundle?) =
        LoginView(context).also {
            it.delegate = this
        }

    override fun observerChanges() {
        viewModel.authenticateEvent.observe(this) {
            when (it) {
                is TaskStatus.Success -> {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                }
                is TaskStatus.Failure -> {
                    contentView.showErrorSnackbar(getString(R.string.login_failed_error))
                }
            }
        }
    }

    override fun onViewCreated(contentView: LoginView, savedInstanceState: Bundle?) {
        if (userPreferences.isLogged()) {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
    }

    // LoginViewDelegate

    override fun authenticate(email: String, password: String) {
        viewModel.authenticate(email, password)
    }
}