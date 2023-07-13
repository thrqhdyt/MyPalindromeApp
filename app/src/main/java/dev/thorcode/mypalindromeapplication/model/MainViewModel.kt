package dev.thorcode.mypalindromeapplication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.lifecycle.viewModelScope
import dev.thorcode.mypalindromeapplication.api.DataItem
import dev.thorcode.mypalindromeapplication.data.UserRepository

class MainViewModel(repository: UserRepository) : ViewModel() {

    val user: LiveData<PagingData<DataItem>> =
        repository.getAllUser().cachedIn(viewModelScope)

}