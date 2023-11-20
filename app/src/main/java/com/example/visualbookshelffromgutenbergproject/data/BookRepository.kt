package com.example.visualbookshelffromgutenbergproject.data

import com.example.visualbookshelffromgutenbergproject.data.models.BookModel

class BookRepository(private val bookApi: BookAPI) {
    suspend fun getData(name: String): BookModel {
        return bookApi.getData(name)
    }
}