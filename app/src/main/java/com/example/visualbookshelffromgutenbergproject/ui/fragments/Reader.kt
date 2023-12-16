package com.example.visualbookshelffromgutenbergproject.ui.fragments

import LoadBookData
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentReaderBinding
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookLocalViewModel
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class Reader : Fragment() {

    private var _binding: FragmentReaderBinding? = null
    private val binding get() = _binding!!
    val args: ReaderArgs by navArgs()
    private var bookURL : Int =0
    private var arg2Value : Int = 0
    private lateinit var loadBookData: LoadBookData
    private lateinit var viewModel: BookLocalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookURL = args.bookURL
        arg2Value = args.realbooid
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReaderBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[BookLocalViewModel::class.java]
        return binding.root
    }
//--------------------------------------------------------------------------------------
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            viewModel.allBooks.observe(viewLifecycleOwner, Observer {
                //https://www.gutenberg.org/ebooks/345.html.images

                //https://www.gutenberg.org/cache/epub/5921/pg5921-images.html
                //https://www.gutenberg.org/cache/epub/5921/pg5921-images.html

                //println("Url den gelen veri : ${it[bookURL-1].text_plain_charsetus_ascii.toString()}")




                val htmlString = "https://www.gutenberg.org/cache/epub/${arg2Value}/pg${arg2Value}-images.html"

                val data = it[bookURL-1].text_plain_charsetus_ascii.toString()

                val document: Document = Jsoup.parse(data)
                val paragraphElements = document.select("p")

                // Her <p> etiketi içindeki metni al ve boş bir satır ekleyerek birleştir
                val finalText = paragraphElements.joinToString("\n\n") { it.text() }

                binding.myTextView.text=finalText

/*
                binding.myWebView.loadDataWithBaseURL(null, it[bookURL-1].text_plain_charsetus_ascii.toString(), "text/html", "UTF-8", null)

                // İsteğe bağlı olarak WebSettings konfigürasyonları yapabilirsiniz
                val webSettings: WebSettings = binding.myWebView.settings
                webSettings.javaScriptEnabled = true*/
            })
    }
}