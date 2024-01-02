package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.visualbookshelffromgutenbergproject.adapters.BookAdapter
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentBookLibraryBinding
import com.example.visualbookshelffromgutenbergproject.utils.ItemClickListener
import com.example.visualbookshelffromgutenbergproject.utils.ItemLongClickListener
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookLocalViewModel


class BookLibrary : Fragment() {

    private var _binding: FragmentBookLibraryBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: BookLocalViewModel
    lateinit var bookAdapter: BookAdapter
    private lateinit var bookList : MutableList<Book>

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

    fun setRecyclerView(bookList: MutableList<Book>){
        binding.searchRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(bookList)
        binding.searchRecyclerview.adapter = bookAdapter

        bookAdapter.setOnItemClickListener(object : ItemClickListener {
            override fun onItemClickListener(position: Int) {
                val bookID = bookList[position].id
                val realBookId = bookList[position].bookId
                val action = BookLibraryDirections.actionBookLibraryToReader(bookID, realBookId!!)
                findNavController().navigate(action)
            }
        })

        bookAdapter.setOnItemLongClickListener(object : ItemLongClickListener{
            override fun onItemLongClickListener(position: Int) {
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setTitle("Delete Book")
                    .setMessage("Are you sure you want to delete this book?")
                    .setPositiveButton("Yes") { _, _ ->
                        // User clicked Yes, proceed with the delete operation
                        viewModel.deleteBook(bookList[position])
                    }
                    .setNegativeButton("No") { _, _ ->
                        // User clicked No, do nothing
                    }
                    .create()

                alertDialog.show()

            }
        })
    }
}