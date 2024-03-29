package com.example.visualbookshelffromgutenbergproject.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imageResource: String?,
    val title: String,
    val author: String,
    val genre: String,
    val copyright: Boolean?,
    val textPlainCharsetusAscii :String?,
    val bookId : Int?,
    val lastPoint : Int?
)