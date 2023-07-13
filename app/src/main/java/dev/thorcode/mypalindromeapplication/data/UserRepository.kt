package dev.thorcode.mypalindromeapplication.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import dev.thorcode.mypalindromeapplication.api.ApiService
import dev.thorcode.mypalindromeapplication.api.DataItem
import dev.thorcode.mypalindromeapplication.database.UserDatabase

class UserRepository(private val userDatabase: UserDatabase, private val apiService: ApiService) {
    fun getAllUser(): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).liveData
    }
}