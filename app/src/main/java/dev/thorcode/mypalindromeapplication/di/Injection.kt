package dev.thorcode.mypalindromeapplication.di

import android.content.Context
import dev.thorcode.mypalindromeapplication.api.ApiConfig
import dev.thorcode.mypalindromeapplication.data.UserRepository
import dev.thorcode.mypalindromeapplication.database.UserDatabase

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return UserRepository(database, apiService)
    }
}