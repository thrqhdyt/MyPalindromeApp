package dev.thorcode.mypalindromeapplication.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getAllUser(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UserResponse
}