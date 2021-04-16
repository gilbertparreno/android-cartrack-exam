package com.gilbertparreno.cartrack.core.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {

    @Provides
    @Singleton
    @Named(value = "userPreferences")
    fun provideSharePreferences(): SharedPreferences {
        return context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
    }
}