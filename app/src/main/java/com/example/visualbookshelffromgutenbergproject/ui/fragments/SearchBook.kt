package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.visualbookshelffromgutenbergproject.R
import com.example.visualbookshelffromgutenbergproject.adapters.BookAdapter
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import com.example.visualbookshelffromgutenbergproject.data.models.BookModel
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentSearchBookBinding
import com.example.visualbookshelffromgutenbergproject.utils.ItemClickListener
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookViewModel


class SearchBook : Fragment() {

    private var _binding: FragmentSearchBookBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: BookViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private var bookList: MutableList<Book> = mutableListOf()
    private lateinit var bookModel: BookModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentSearchBookBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize RecyclerView and BookAdapter
        recyclerView = binding.searchRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(mutableListOf())
        Glide.with(this)
            .load(R.drawable.seacrh_book)
            .into(binding.gifImageView)

        // Start initializing some books
        getOldData()

        binding.searchBox.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findBook()
            }
            true
        }
    }

    fun findBook() {
        showGif()

        viewModel.clearData()
        bookList.clear()
        bookAdapter.notifyDataSetChanged()

        getData(binding.searchBox.text.toString())

        performSearch()
    }

    private fun getOldData() {
        viewModel.books.observe(viewLifecycleOwner) { books ->
            books?.let {
                bookModel = it

                // Process each book in the result and add it to the bookList
                for (i in it.results) {
                    val author = i.authors?.get(0)?.name
                    val title = i.title
                    val genre = if (i.bookshelves?.isNotEmpty() == true) i.bookshelves[0] else "Unknown Genre"
                    val copyright = i.copyright
                    val image = i.formats?.image_jpeg
                    val bookContent = i.formats?.text_html

                    val book = Book(
                        imageResource = image,
                        title = title.toString(),
                        author = author.toString(),
                        genre = genre,
                        copyright = copyright,
                        textPlainCharsetusAscii = bookContent,
                        bookId = i.id,
                        lastPoint = 0
                    )
                    // Add the book to the list
                    addBook(book)
                }
                // Set up the RecyclerView with the updated bookList
                setRecyclerView(it)
            }
        }
    }

    private fun getData(name: String) {
        // Fetch book data from the ViewModel
        viewModel.loadData(name)
        viewModel.books.observe(viewLifecycleOwner) { books ->
            books?.let {
                bookModel = it

                // Process each book in the result and add it to the bookList
                for (i in it.results) {
                    val author = i.authors?.get(0)?.name
                    val title = i.title
                    val genre = if (i.bookshelves?.isNotEmpty() == true) i.bookshelves[0] else "Unknown Genre"
                    val copyright = i.copyright
                    val image = i.formats?.image_jpeg
                    val bookContent = i.formats?.text_html

                    val book = Book(
                        imageResource = image,
                        title = title.toString(),
                        author = author.toString(),
                        genre = genre,
                        copyright = copyright,
                        textPlainCharsetusAscii = bookContent,
                        bookId = i.id,
                        lastPoint = 0
                    )
                    // Add the book to the list
                    addBook(book)
                }
                // Set up the RecyclerView with the updated bookList
                setRecyclerView(it)
            }
        }
    }

    private fun setRecyclerView(bookModel: BookModel) {
        recyclerView = binding.searchRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(bookList)
        recyclerView.adapter = bookAdapter

        // Set item click listener for navigating to book details
        bookAdapter.setOnItemClickListener(object : ItemClickListener {
            override fun onItemClickListener(position: Int) {
                val bookRes = bookModel.results[position]
                val action = SearchBookDirections.actionSearchBook2ToShowBookDetails(bookRes)
                findNavController().navigate(action)
            }
        })

        // Hide progress bar after data is loaded
        hideGif()
    }

    private fun addBook(book: Book) {
        // Add the book to the bookList
        bookList = (bookList + listOf(book)).toMutableList()
    }

    // This method can be called to show a GIF.
    private fun showGif() {
        binding.gifImageView.visibility = View.VISIBLE
    }

    // This method can be called to hide a GIF.
    private fun hideGif() {
        binding.gifImageView.visibility = View.INVISIBLE
    }

    private fun performSearch() {
        binding.searchBox.text!!.clear()
        hideKeyboard()
        binding.searchBox.clearFocus()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(InputMethodManager::class.java)
        imm.hideSoftInputFromWindow(binding.searchBox.windowToken, 0)
    }
}
