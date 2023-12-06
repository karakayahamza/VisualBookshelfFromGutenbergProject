package com.example.visualbookshelffromgutenbergproject.ui.fragments

import LoadBookData
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentReaderBinding
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookLocalViewModel
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookViewModel


class Reader : Fragment() {

    private var _binding: FragmentReaderBinding? = null
    private val binding get() = _binding!!
    val args: ReaderArgs by navArgs()
    private var bookURL : Long =0
    private lateinit var loadBookData: LoadBookData
    private lateinit var viewModel: BookLocalViewModel
    lateinit var bookList : MutableList<Book>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookURL = args.bookURL
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReaderBinding.inflate(inflater, container, false)

        return binding.root
    }
//--------------------------------------------------------------------------------------
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allBooks.observe(viewLifecycleOwner) { books ->
            bookList = books.toMutableList()
        }

        //loadBooks(bookID)

        //val fetchTextTask = LoadBookData()
        //fetchTextTask.execute("https://www.gutenberg.org/files/$bookID/$bookID.txt")


           // bookList[bookURL].bookId


        println("Book URL:"+bookURL)

        val url = "https://www.gutenberg.org/cache/epub/84/pg84-images.html"
        val loadBookData = LoadBookData(requireContext()) // Assuming webView is the id of your WebView
        loadBookData.execute(url)



        //loadBookData.execute("https://www.gutenberg.org/cache/epub/$bookID/pg$bookID-images.html")


    }


}