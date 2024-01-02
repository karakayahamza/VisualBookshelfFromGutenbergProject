package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentReaderBinding
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookLocalViewModel
import org.jsoup.Jsoup

class Reader : Fragment() {

    private var _binding: FragmentReaderBinding? = null
    private val binding get() = _binding!!
    private val args: ReaderArgs by navArgs()
    private var bookURL: Int = 0
    private var arg2Value: Int = 0
    private lateinit var viewModel: BookLocalViewModel
    private lateinit var webView: WebView
    private lateinit var sharedPreferences: SharedPreferences
    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrieveArguments()
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReaderBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[BookLocalViewModel::class.java]

        isDarkMode = sharedPreferences.getBoolean(KEY_DARK_MODE, false)

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var content: String = ""

        viewModel.allBooks.observe(viewLifecycleOwner) { books ->
            val targetBook = books.find { it.bookId == arg2Value }

            targetBook?.let {
                logBookDetails(it)
                content = it.textPlainCharsetusAscii.toString()
            } ?: showError()

            setUpWebView()
            loadData(content)
            restoreScrollPosition(arg2Value) // Pass the bookId for scroll restoration
        }
    }

    override fun onPause() {
        super.onPause()
        saveScrollPositionToSharedPreferences(arg2Value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retrieveArguments() {
        args.apply {
            this@Reader.bookURL = this.bookURL
            arg2Value = this.realbooid
        }
    }

    private fun logBookDetails(book: Book) {
        Log.d("Book Details", "${book.title} : ${book.author}")
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Something went wrong! Try again.", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webView = binding.myWebView
        val webSettings: WebSettings = webView.settings

        with(webSettings) {
            javaScriptEnabled = true
            allowFileAccess = true
            allowUniversalAccessFromFileURLs = true
            setSupportZoom(false)
            builtInZoomControls = false
            webSettings.displayZoomControls = false
        }

        webView.webViewClient = WebViewClient()
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val alignBodyContentToLeft = """
                        |(function() {
                        |    var bodyElement = document.querySelector('body');
                        |    if (bodyElement) {
                        |        bodyElement.style.textAlign = 'left';
                        |        bodyElement.style.padding = '0';
                        |        bodyElement.style.margin = '0';
                        |    }
                        |})();
                    """.trimMargin()

                webView.evaluateJavascript(alignBodyContentToLeft, null)
                applyDarkMode(isDarkMode)
            }
        }
    }

    fun removeImages(html: String): String {
        val document = Jsoup.parse(html)

        // Remove image tags
        document.select("img").remove()

        // Get the cleaned HTML content
        val cleanedHtml = document.html()

        return cleanedHtml
    }

    private fun loadData(data: String) {
        activity?.runOnUiThread {
            if (data.isNotEmpty()) {
                // Extract and remove styling from the body in HTML content
                val modifiedHtmlContent = removeImages(data)

                // Load the modified HTML content into WebView
                webView.loadDataWithBaseURL(null, modifiedHtmlContent, "text/html", "UTF-8", null)

            }
        }
    }

    private fun saveScrollPositionToSharedPreferences(bookId: Int) {
        val scrollPosition = webView.scrollY
        Log.d("Scroll Y", scrollPosition.toString())
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("scroll_position_$bookId", scrollPosition)
            apply()
        }
    }

    private fun restoreScrollPosition(bookId: Int) {
        val scrollPositionKey = "scroll_position_$bookId"
        val scrollPosition = requireActivity().getPreferences(Context.MODE_PRIVATE).getInt(scrollPositionKey, 0)

        webView.post {
            webView.scrollTo(0, scrollPosition)
        }
    }

    private fun applyDarkMode(isDarkMode: Boolean) {
        webView.loadUrl(
            "javascript:(function() {" +
                    // Set background color
                    "document.body.style.backgroundColor = '${if (isDarkMode) "#202020" else "#ffffff"}';" +

                    // Set text color
                    "document.body.style.color = '${if (isDarkMode) "#ffffff" else "#000000"}';" +

                    // Set text color for all text elements
                    "var allTextElements = document.querySelectorAll('body, p, h1, h2, h3, h4, h5, h6, span, div, a, li, td, th');" +
                    "for (var i = 0; i < allTextElements.length; i++) {" +
                    "    allTextElements[i].style.color = '${if (isDarkMode) "#ffffff" else "#000000"}';" +
                    "}" +
                    "})()"
        )
    }

    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
    }
}
