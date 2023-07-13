package dev.thorcode.mypalindromeapplication.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.thorcode.mypalindromeapplication.di.Injection
import java.lang.IllegalStateException


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalStateException("Uknown ViewModel class")
    }
}