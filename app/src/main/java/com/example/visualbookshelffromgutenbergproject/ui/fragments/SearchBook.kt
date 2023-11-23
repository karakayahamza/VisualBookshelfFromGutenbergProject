package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookViewModel
import com.example.visualbookshelffromgutenbergproject.utils.ItemClickListener
import com.example.visualbookshelffromgutenbergproject.adapters.BookAdapter
import com.example.visualbookshelffromgutenbergproject.data.models.BookModel
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentSearchBookBinding
import kotlinx.coroutines.cancel


class SearchBook : Fragment() {
    private var _binding: FragmentSearchBookBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private var bookList: MutableList<Book> = mutableListOf()
    private lateinit var bookModel: BookModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBookBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[BookViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.search.setOnClickListener {
            viewModel.viewModelScope.cancel()
            bookList.clear()
            getData(binding.searchBook.text.toString())
        }
    }

    private fun getData(name:String){
        viewModel.loadData(name)
        viewModel.books.observe(viewLifecycleOwner){books ->
            books?.let {
                bookModel = it

                for(i in it.results){

                    val author = i.authors?.get(0)?.name
                    val title = i.title
                    val genre = if (i.bookshelves?.isNotEmpty() == true) i.bookshelves.get(0) else "Unknown Genre"
                    val copyright = i.copyright
                    val image = i.formats?.image_jpeg
                    val plainText = i.formats?.text_plain

                    val book = Book(
                        imageResource = image,
                        title = title.toString(),
                        author = author.toString(),
                        genre = genre,
                        copyright = copyright,
                        text_plain_charsetus_ascii = plainText.toString()
                    )

                    addBook(book)
                }
                setRecyclerView(it)
            }
        }
    }


    private fun setRecyclerView(bookModel: BookModel){
        recyclerView = binding.searchRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(bookList)
        recyclerView.adapter = bookAdapter



        bookAdapter.setOnItemClickListener(object : ItemClickListener {
            override fun onItemClickListener(position: Int) {

                val bookRes = bookModel.results[position]
                val action = SearchBookDirections.actionSearchBook2ToShowBookDetails(bookRes)
                findNavController().navigate(action)

                Toast.makeText(requireContext(),bookList[position].author,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun addBook(book: Book) {
        bookList = (bookList + listOf(book)).toMutableList()
    }
}