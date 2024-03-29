package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.visualbookshelffromgutenbergproject.R
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import com.example.visualbookshelffromgutenbergproject.data.models.BookModel.Result
import com.example.visualbookshelffromgutenbergproject.data.remote.helper.LoadBookData
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentShowBookDetailsBinding
import com.example.visualbookshelffromgutenbergproject.viewmodel.BookLocalViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowBookDetails : Fragment() {
    private val args: ShowBookDetailsArgs by navArgs()
    private lateinit var selectedId : Result
    private var _binding: FragmentShowBookDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookLocalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (args.framentkey==null){
            Toast.makeText(requireContext(),"We encountered an error",Toast.LENGTH_SHORT).show()
        }
        selectedId = args.framentkey

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowBookDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[BookLocalViewModel::class.java]
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


        val title = if (selectedId.title!!.isNotEmpty()) selectedId.title else "Unknown Title"
        val author = if (selectedId.authors!![0].name!!.isNotEmpty()) selectedId.authors!![0].name else "Unknown Author"
        val genre = if (selectedId.bookshelves?.isNotEmpty() == true) selectedId.bookshelves!![0] else "Unknown Genre"
        val language = if (selectedId.languages!![0].isNotEmpty()) selectedId.languages!![0] else "Unknown Language"
        //val textPlain = selectedId.formats?.textPlainCharsetusAscii ?: "Unknown Language"

        val bookContent = "https://www.gutenberg.org/cache/epub/${selectedId.id}/pg${selectedId.id}-images.html"

        val id = selectedId.id

        binding.titleTextView.text = "Title: $title"
        binding.authorTextView.text = "Author: $author"
        binding.genreTextView.text = "Genre: $genre"
        binding.languageTextView.text = "Language: $language"


        binding.favoriteButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                binding.progressBar.visibility = View.VISIBLE

                try {
                    val result = withContext(Dispatchers.IO) {
                        val loadBookData = LoadBookData()
                        loadBookData.execute(bookContent).get()
                    }

                    val newBook = Book(
                        imageResource = selectedId.formats?.image_jpeg,
                        title = title.toString(),
                        author = author.toString(),
                        genre = genre,
                        copyright = true,
                        textPlainCharsetusAscii = result ?: "Unknown",
                        bookId = id,
                        lastPoint = 0
                    )


                    println(result.toString())

                    viewModel.insertOrUpdate(newBook)

                } catch (e: Exception) {
                    Log.d("HATA",e.toString())
                } finally {
                    binding.progressBar.visibility = View.GONE
                    // Veritabanı işlemi tamamlandığında Toast mesajı göster
                    Toast.makeText(requireContext(), "Book was downloaded", Toast.LENGTH_SHORT).show()
                    val action = ShowBookDetailsDirections.actionShowBookDetailsToSearchBook2()
                    findNavController().navigate(action)
                    findNavController().popBackStack(R.id.SearchBookFragment, false)
                }
            }


        }

    }
}