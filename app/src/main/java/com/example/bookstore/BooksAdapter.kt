package com.example.bookstore

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import kotlinx.android.synthetic.main.book_item.view.*

class BooksAdapter(private val books: MutableList<Book>) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {
    private val helper = BookModel()
    val realm = Realm.getDefaultInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.book_title.text = books[position].book_title
        holder.author.text = books[position].author
        holder.book_cover.setImageURI(Uri.parse(books[position].book_cover))
    }

    fun addItem(item:Book) {
        books.add(item)
        notifyItemInserted(books.size)
    }

    fun removeAt(position: Int) :Int{
        val currentDeleted = books[position].id
        helper.removeBook(realm, currentDeleted!!)
        books.removeAt(position)
        notifyItemRemoved(position)

        return position
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
            var book_title:TextView = itemView.book_title
            var author:TextView = itemView.author
            var book_cover:ImageView = itemView.book_cover

        init {
            itemView.setOnClickListener(){
                println("item clicked$adapterPosition")
                val selectedBook = books[adapterPosition].id
                val intent = Intent(itemView.context, BookDetails::class.java)
                intent.putExtra("EXTRA_BookID", selectedBook)
                itemView.context.startActivity(intent)
            }
        }
    }

}

