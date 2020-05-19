package com.example.bookstore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.book_item.view.*
import org.w3c.dom.Text

class BooksAdapter(private val books: MutableList<Book>) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.book_title.text = books[position].book_title
        holder.author.text = books[position].author
        Picasso.get().load(books[position].book_url)
            .fit()
            .into(holder.book_cover)
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
            var book_title:TextView = itemView.book_title
            var author:TextView = itemView.author
            var book_cover:ImageView = itemView.book_cover
    }

}
