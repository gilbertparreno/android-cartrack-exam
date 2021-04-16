package com.gilbertparreno.cartrack

import android.app.Application
import com.gilbertparreno.cartrack.core.di.AppComponent
import com.gilbertparreno.cartrack.core.di.DaggerAppComponent
import com.gilbertparreno.cartrack.core.extensions.launch
import com.gilbertparreno.cartrack.core.extensions.logError
import com.gilbertparreno.cartrack.core.extensions.logInfo
import com.gilbertparreno.cartrack.core.networking.di.NetworkModule
import com.gilbertparreno.cartrack.core.room.daos.UserDao
import com.gilbertparreno.cartrack.core.room.di.RoomModule
import com.gilbertparreno.cartrack.core.room.entities.User
import com.gilbertparreno.cartrack.core.utils.security.Crypto
import kotlinx.coroutines.GlobalScope
import timber.log.Timber
import javax.inject.Inject

class CartrackApplication : Application() {

    @Inject lateinit var userDao: UserDao

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .roomModule(RoomModule(this))
            .build()
            .also {
                it.inject(this)
            }


        addTestUsersIfNeeded()
    }

    private fun addTestUsersIfNeeded() {
        GlobalScope.launch(
            work = {
                if (userDao.getUsers().isEmpty()) {
                    listOf(
                        Pair("user1@gmail.com", "user1P@ssword"),
                        Pair("user2@gmail.com", "user2P@ssword"),
                        Pair("user3@gmail.com", "user3P@ssword")
                    ).forEach {
                        val map = Crypto.encrypt(it.second)
                        userDao.insertUsers(
                            User(
                                email = it.first,
                                passwordSalt = map["salt"]!!,
                                passwordIv = map["iv"]!!,
                                passwordEncrypted = map["encrypted"]!!
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