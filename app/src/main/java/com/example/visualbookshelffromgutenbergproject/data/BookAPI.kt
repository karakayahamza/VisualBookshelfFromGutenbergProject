package com.example.visualbookshelffromgutenbergproject.data

import com.example.visualbookshelffromgutenbergproject.data.models.BookModel
import retrofit2.http.GET
import retrofit2.http.Query

interface BookAPI {
    @GET("books/?")
    suspend fun getData(
        @Query("search") name: String?
    ): BookModel
}