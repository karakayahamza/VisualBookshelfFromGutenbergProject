package com.example.visualbookshelffromgutenbergproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visualbookshelffromgutenbergproject.adapters.CustomAdapter
import com.example.visualbookshelffromgutenbergproject.R
import com.example.visualbookshelffromgutenbergproject.data.models.BookModel

class FavoriteBooks : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_favorite_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)


        /*val dataObject: BookModel? = arguments?.getParcelable("bookDetails")

        if (dataObject!=null){

            println(dataObject.results[0].authors[0].name.toString())

        }*/




        /*recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val data = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        val adapter = CustomAdapter(data) // Özel bir adapter kullanmanız gerekecek
        recyclerView.adapter = adapter*/

    }


}