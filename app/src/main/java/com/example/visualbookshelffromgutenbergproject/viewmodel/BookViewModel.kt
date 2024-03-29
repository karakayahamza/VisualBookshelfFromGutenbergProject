package com.example.visualbookshelffromgutenbergproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visualbookshelffromgutenbergproject.data.models.BookModel
import com.example.visualbookshelffromgutenbergproject.data.remote.BookAPI
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
    val error = MutableLiveData<String?>()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gutendex.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val bookApi = retrofit.create(BookAPI::class.java)
        bookRepository = BookRepository(bookApi)
    }

    fun loadData(name: String) {
        Log.d("SearchBookViewModel", "loadData is called") // Log eklemesi
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

    private fun handleError(throwable: Throwable) {
        error.value = "An error occurred: ${throwable.message}"
        println("$throwable")
    }

    private fun handleResults(bookModel: BookModel) {
        books.value = bookModel
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun clearData() {
        books.value = null
    }

}