package com.gilbertparreno.cartrack.authentication.viewModels

import androidx.lifecycle.ViewModel
import com.gilbertparreno.cartrack.authentication.managers.AuthenticationManager
import com.gilbertparreno.cartrack.core.extensions.launch
import kotlinx.coroutines.GlobalScope
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager
) : ViewModel() {

    fun login(email: String, password: String) {
        GlobalScope.launch(work = {
            authenticationManager.authenticateUser(email, password)
        }, onSuccess = {
            Timber.d("test")
        }, onFailure = {
            Timber.d(it)
        })
    }
}