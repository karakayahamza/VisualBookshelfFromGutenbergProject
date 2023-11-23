package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.visualbookshelffromgutenbergproject.R
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentBookLibraryBinding
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentReaderBinding
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookLocalViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Reader : Fragment() {

    private var _binding: FragmentReaderBinding? = null
    private val binding get() = _binding!!
    val args: ReaderArgs by navArgs()
    private lateinit var bookText : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (args.bookString!=null){
            bookText = args.bookString!!
        }
        else println("Value is null..")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReaderBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadBooks(bookText)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun loadBooks(bookURL: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(bookURL)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                        response.append("\n")
                    }

                    reader.close()

                    withContext(Dispatchers.Main) {
                        val data = response.toString()

                        println("Books:$data")
                        binding.bookText.text = data
                    }
                } else if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                    // Handle redirection
                    val newUrl = connection.getHeaderField("Location")
                    if (newUrl != null) {
                        // Recursive call with the new URL
                        loadBooks(newUrl)
                    }
                } else {
                    println("HTTP Request Failed. Response Code: $responseCode")
                }

                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}