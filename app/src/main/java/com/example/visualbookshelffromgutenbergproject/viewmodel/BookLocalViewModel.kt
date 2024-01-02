package com.example.visualbookshelffromgutenbergproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.visualbookshelffromgutenbergproject.data.local.BookDatabase
import com.example.visualbookshelffromgutenbergproject.data.local.repository.BookLocalRepository
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BookLocalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookLocalRepository
    private val _insertOrUpdateComplete = MutableLiveData<Boolean>()

    init {
        val noteDao = BookDatabase.getDatabase(application).bookDao()
        repository = BookLocalRepository(noteDao)
    }

    val allBooks: LiveData<List<Book>> = repository.getAllBooks

    fun insertOrUpdate(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertBook(book)
        _insertOrUpdateComplete.postValue(true)
    }

    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteBook(book)
        _insertOrUpdateComplete.postValue(true)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}