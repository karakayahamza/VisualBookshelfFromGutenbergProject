package com.example.visualbookshelffromgutenbergproject.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.visualbookshelffromgutenbergproject.data.models.Book
import com.example.visualbookshelffromgutenbergproject.utils.ItemClickListener
import com.example.visualbookshelffromgutenbergproject.R
import com.example.visualbookshelffromgutenbergproject.databinding.BookItemBinding

class BookAdapter(private val bookList: MutableList<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private var itemClickListener: ItemClickListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)

    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class BookViewHolder(binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val bookImageView: ImageView = binding.bookImageView
        private val bookTitleTextView: TextView = binding.textViewTitle
        private val bookAuthorTextView: TextView = binding.textViewAuthor
        private val bookGenreTextView: TextView = binding.genre
        private val copyrightTextView: TextView = binding.copyright


        init {
            itemView.setOnClickListener{
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION){
                    itemClickListener?.onItemClickListener(position)
                }
            }

        }


        @SuppressLint("SetTextI18n")
        fun bind(book: Book) {
            val targetWidth = 200
            val targetHeight = 200

            Glide.with(itemView.context)
                .load(book.imageResource)
                .override(targetWidth, targetHeight)
                .centerCrop()
                .into(bookImageView)

            bookTitleTextView.text = book.title
            bookAuthorTextView.text = "Author: ${book.author}"
            bookGenreTextView.text = "Genre: ${book.genre}"
            copyrightTextView.text = "Copyright: ${book.copyright}"
        }
    }
}