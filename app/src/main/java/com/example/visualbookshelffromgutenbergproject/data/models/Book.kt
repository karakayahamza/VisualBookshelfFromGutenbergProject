package com.example.visualbookshelffromgutenbergproject.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val imageResource: String?,
    val title: String,
    val author: String,
    val genre: String,
    val copyright: Boolean?,
    val text_plain_charsetus_ascii :String?
)