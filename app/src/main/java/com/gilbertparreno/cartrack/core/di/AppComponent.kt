package com.gilbertparreno.cartrack.core.di

import com.gilbertparreno.cartrack.CartrackApplication
import com.gilbertparreno.cartrack.core.networking.di.NetworkModule
import com.gilbertparreno.cartrack.core.room.di.RoomModule
import com.gilbertparreno.cartrack.main.fragments.MainFragment
import com.gilbertparreno.cartrack.userDetails.fragments.UserDetailsFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, RoomModule::class])
@Singleton
interface AppComponent {

    fun inject(application: CartrackApplication)

    fun inject(mainFragment: MainFragment)
    fun inject(userDetailsFragment: UserDetailsFragment)
}