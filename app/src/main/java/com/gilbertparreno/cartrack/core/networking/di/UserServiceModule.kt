package com.gilbertparreno.cartrack.core.networking.di

import com.gilbertparreno.cartrack.core.networking.services.UserService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class UserServiceModule {

    @Provides
    @Singleton
    fun provideBusinessService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}