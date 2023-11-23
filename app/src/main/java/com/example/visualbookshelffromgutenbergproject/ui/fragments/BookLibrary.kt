package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visualbookshelffromgutenbergproject.adapters.BookAdapter
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentBookLibraryBinding
import com.example.visualbookshelffromgutenbergproject.utils.ItemClickListener
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookLocalViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class BookLibrary : Fragment() {

    private var _binding: FragmentBookLibraryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookLocalViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    lateinit var bookList : MutableList<Book>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[BookLocalViewModel::class.java]

        _binding = FragmentBookLibraryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.allBooks.observe(viewLifecycleOwner) { books ->
            bookList = books.toMutableList()
            setRecyclerView(bookList)
        }
    }

    private fun setRecyclerView(bookList: MutableList<Book>){
        recyclerView = binding.searchRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(bookList)
        recyclerView.adapter = bookAdapter

        bookAdapter.setOnItemClickListener(object : ItemClickListener {
            override fun onItemClickListener(position: Int) {


                println("Book position : " + bookList[position])

                val bookString = bookList[position].text_plain_charsetus_ascii
                println("Book String: $bookString")
                val action = BookLibraryDirections.actionBookLibraryToReader(bookString)
                findNavController().navigate(action)


                Toast.makeText(requireContext(),bookList[position].author, Toast.LENGTH_LONG).show()
            }
        })
    }
}