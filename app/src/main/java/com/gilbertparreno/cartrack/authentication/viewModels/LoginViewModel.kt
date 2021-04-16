package com.gilbertparreno.cartrack.authentication.viewModels

import android.util.Base64
import androidx.lifecycle.ViewModel
import com.gilbertparreno.cartrack.BuildConfig
import com.gilbertparreno.cartrack.authentication.managers.AuthenticationManager
import com.gilbertparreno.cartrack.core.extensions.SingleLiveEvent
import com.gilbertparreno.cartrack.core.extensions.launch
import com.gilbertparreno.cartrack.core.extensions.logError
import com.gilbertparreno.cartrack.core.extensions.logInfo
import com.gilbertparreno.cartrack.core.networking.taskStatus.TaskStatus
import com.gilbertparreno.cartrack.core.providers.CoroutineContextProvider
import com.gilbertparreno.cartrack.core.room.daos.UserDao
import com.gilbertparreno.cartrack.core.room.entities.User
import com.gilbertparreno.cartrack.core.security.Encryption
import kotlinx.coroutines.GlobalScope
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val userDao: UserDao
) : ViewModel() {

    val authenticateEvent = SingleLiveEvent<TaskStatus<Any>>()

    init {
        addTestUsersIfNeeded()
    }

    fun authenticate(email: String, password: String) {
        GlobalScope.launch(
            coroutineContextProvider = coroutineContextProvider,
            work = {
                authenticationManager.authenticateUser(email, password)
            },
            onSuccess = {
                authenticateEvent.value = TaskStatus.success()
            },
            onFailure = {
                authenticateEvent.value = TaskStatus.error(it)
            }
        )
    }

    private fun addTestUsersIfNeeded() {
        GlobalScope.launch(
            coroutineContextProvider = coroutineContextProvider,
            work = {
                if (userDao.getUsers().isEmpty()) {
                    listOf(
                        Pair("user1@gmail.com", "user1P@ssword"),
                        Pair("user2@gmail.com", "user2P@ssword"),
                        Pair("user3@gmail.com", "user3P@ssword")
                    ).forEach {
                        val map = Encryption.encrypt(
                            it.second.toByteArray(Charsets.UTF_8),
                            BuildConfig.MASTER_PASSWORD.toCharArray()
                        )
                        userDao.insertUsers(
                            User(
                                email = it.first,
                                passwordSalt = Base64.encodeToString(map["salt"], Base64.NO_WRAP),
                                passwordIv = Base64.encodeToString(map["iv"], Base64.NO_WRAP),
                                passwordEncrypted = Base64.encodeToString(
                                    map["encrypted"],
                                    Base64.NO_WRAP
                                )
                            )
                        )
                    }
                }
            }, onSuccess = {
                logInfo("Test users created")
            }, onFailure = {
                logError(it)
            }
        )
    }
}