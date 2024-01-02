package com.example.visualbookshelffromgutenbergproject.data.local.repository

import androidx.lifecycle.LiveData
import com.example.visualbookshelffromgutenbergproject.data.local.dao.BookDao
import com.example.visualbookshelffromgutenbergproject.data.models.Book

class BookLocalRepository(private val bookDao: BookDao) {
    fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }
    fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    val getAllBooks: LiveData<List<Book>> = bookDao.getAllBooks()
}