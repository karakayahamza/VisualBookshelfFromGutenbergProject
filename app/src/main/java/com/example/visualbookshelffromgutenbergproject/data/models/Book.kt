package com.example.visualbookshelffromgutenbergproject.data.models


data class Book(
    val imageResource: String?,
    val title: String?,
    val author: String?,
    val genre: String,
    val copyright: Boolean?
)