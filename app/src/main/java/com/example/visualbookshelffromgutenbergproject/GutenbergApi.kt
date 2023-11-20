package com.example.visualbookshelffromgutenbergproject

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object GutenbergApi {

    fun getBookList(searchQuery: String): String? {
        val apiUrl = "https://www.gutenberg.org/ebooks/search/?query=$searchQuery"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(apiUrl)
            .build()

        return try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}