package com.example.visualbookshelffromgutenbergproject.data.remote

import com.example.visualbookshelffromgutenbergproject.data.models.BookModel
import retrofit2.http.GET
import retrofit2.http.Query

interface BookAPI {
    //&mime_type=text%2Fhtml
    @GET("books/?")
    suspend fun getData(@Query("search") name: String?): BookModel
}