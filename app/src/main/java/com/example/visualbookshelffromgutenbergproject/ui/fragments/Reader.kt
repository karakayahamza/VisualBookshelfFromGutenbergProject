package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.visualbookshelffromgutenbergproject.LoadBookData
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentReaderBinding


class Reader : Fragment() {

    private var _binding: FragmentReaderBinding? = null
    private val binding get() = _binding!!
    val args: ReaderArgs by navArgs()
    private var bookURL : String =""
    private lateinit var loadBookData: LoadBookData


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //loadBooks(bookID)

        //val fetchTextTask = LoadBookData()
        //fetchTextTask.execute("https://www.gutenberg.org/files/$bookID/$bookID.txt")


        println("Book URL:"+bookURL)

        val bookUrl = "https://www.gutenberg.org/files/1342/1342-0.txt"
        loadBookData = LoadBookData(binding.bookText)
        loadBookData.execute(bookUrl)



        //loadBookData.execute("https://www.gutenberg.org/cache/epub/$bookID/pg$bookID-images.html")


    }


}