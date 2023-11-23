package com.example.visualbookshelffromgutenbergproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visualbookshelffromgutenbergproject.data.remote.BookAPI
import com.example.visualbookshelffromgutenbergproject.data.models.BookModel
import com.example.visualbookshelffromgutenbergproject.data.remote.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookViewModel: ViewModel() {
    private val bookRepository: BookRepository

    val books = MutableLiveData<BookModel?>()
    val error = MutableLiveData<Boolean?>()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gutendex.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val bookApi = retrofit.create(BookAPI::class.java)
        bookRepository = BookRepository(bookApi)
    }

    fun loadData(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val bookModel = bookRepository.getData(name)
                withContext(Dispatchers.Main) {
                    handleResults(bookModel)
                }
            } catch (throwable: Throwable) {
                withContext(Dispatchers.Main) {
                    handleError(throwable)
                }
            }
        }
    }

    private fun handleResults(bookModel: BookModel) {
        books.value = bookModel
        error.value = false
    }

    private fun handleError(throwable: Throwable) {
        error.value = true
        println("$throwable")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}