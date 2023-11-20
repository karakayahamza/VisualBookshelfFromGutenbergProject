package com.example.visualbookshelffromgutenbergproject.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.visualbookshelffromgutenbergproject.R
import com.example.visualbookshelffromgutenbergproject.data.models.BookModel
import com.example.visualbookshelffromgutenbergproject.data.models.BookModel.Result
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentSearchBookBinding
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentShowBookDetailsBinding
import com.example.visualbookshelffromgutenbergproject.view.viewmodel.BookViewModel

class ShowBookDetails : Fragment() {
    val args: ShowBookDetailsArgs by navArgs()
    lateinit var selectedId : Result
    private var _binding: FragmentShowBookDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (args.framentkey==null){
            println("NULL")
        }
    //    selectedId = args.framentkey
        //
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowBookDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val targetWidth = 150
        val targetHeight = 200

        Glide.with(this)
            .load(selectedId.formats?.image_jpeg)
            .override(targetWidth, targetHeight)
            .centerCrop()
            .into(binding.bookImageView)

        binding.titleTextView.text = "Book Name: " + selectedId.title
        binding.authorTextView.text = "Author: " + selectedId.authors!![0]
        binding.genreTextView.text = "Genre: " + selectedId.bookshelves!![0]
        binding.languageTextView.text = "Language: "+ selectedId.languages!![0]




    }
}