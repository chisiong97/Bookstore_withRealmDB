package com.example.bookstore

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.book_item.view.*
import org.w3c.dom.Text

class BooksAdapter(private val books: MutableList<Book>, var arrStatus:Int) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.book_title.text = books[position].book_title
        holder.author.text = books[position].author
        holder.book_cover.setImageURI(books[position].book_cover)
        /*
        Picasso.get().load(books[position].book_cover)
                .fit()
                .into(holder.book_cover)
        println("Test load:" + books[position].book_cover)

         */


    }

    fun addItem(item:Book) {
        books.add(item)
        notifyItemInserted(books.size)
    }

    fun removeAt(position: Int) {
        books.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
            var book_title:TextView = itemView.book_title
            var author:TextView = itemView.author
            var book_cover:ImageView = itemView.book_cover

        init {
            itemView.setOnClickListener(){
                println("item clicked$adapterPosition")
                val intent = Intent(itemView.context, BookDetails::class.java)
                intent.putExtra("EXTRA_adapterPosition", adapterPosition)
                intent.putExtra("EXTRA_arrStatus", arrStatus)
                intent.putParcelableArrayListExtra("EXTRA_bookArray", ArrayList(books))
                itemView.context.startActivity(intent)
            }
        }
    }

}

