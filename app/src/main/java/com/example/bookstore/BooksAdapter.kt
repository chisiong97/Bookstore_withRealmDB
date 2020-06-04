package com.example.bookstore

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.book_item.view.*


class BooksAdapter(val books: RealmResults<Book>, private val currentActivity: Activity) :
    RealmRecyclerViewAdapter<Book, BooksAdapter.ViewHolder>(books,true)
{

    private val helper = BookModel()
    val realm = Realm.getDefaultInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.book_title.text = books[position]?.book_title
        holder.author.text = books[position]?.author

        println("Current book cover: " + books[position]?.book_cover)
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        Glide.with(holder.itemView.context).load(books[position]?.book_cover).into(holder.book_cover)

    }

    fun removeAt(position: Int) :Int
    {
        val currentDeleted = books[position]?.id
        println("Current deleted ID: "+ currentDeleted )
        println("Current deleted position: "+ position)
        helper.removeBook(realm, currentDeleted!!)
        notifyItemRemoved(position)

        return position
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
            var book_title:TextView = itemView.book_title
            var author:TextView = itemView.author
            var book_cover:ImageView = itemView.book_cover

        init
        {
            itemView.setOnClickListener()
            {
                println("item clicked$adapterPosition")
                val selectedBook = books[adapterPosition]?.id
                val intent = Intent(itemView.context, BookDetails::class.java)
                intent.putExtra("EXTRA_BookID", selectedBook)
                currentActivity.startActivityForResult(intent,1)
            }
        }
    }

}



