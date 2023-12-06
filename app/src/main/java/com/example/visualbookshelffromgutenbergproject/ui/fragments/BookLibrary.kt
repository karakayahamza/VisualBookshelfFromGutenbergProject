package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visualbookshelffromgutenbergproject.adapters.BookAdapter
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentBookLibraryBinding
import com.example.visualbookshelffromgutenbergproject.utils.ItemClickListener
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookLocalViewModel


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
                val bookID = bookList[position].id
                val action = BookLibraryDirections.actionBookLibraryToReader(bookID)
                findNavController().navigate(action)
            }
        })
    }
}