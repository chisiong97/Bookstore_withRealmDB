package com.example.bookstore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_book_list.*
import kotlinx.android.synthetic.main.custom_action_bar_layout.*
import androidx.recyclerview.widget.ItemTouchHelper


class BookList : AppCompatActivity() {
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)
        //setSupportActionBar(findViewById(R.id.custom_action_bar_layout))

        //TODO: To use DB to populate
        //Note: user upload images..not url
        var books = mutableListOf<Book>(
            (Book(book_title = "Programming", author = "CS", book_url = "https://images-na.ssl-images-amazon.com/images/I/514axA2lwpL.jpg")),
            (Book(book_title = "Dictionary", author = "CS", book_url = "https://is1-ssl.mzstatic.com/image/thumb/Purple123/v4/49/98/2f/49982f6e-a96e-3b94-95bf-bcbb05dd5c5e/AppIcon-0-85-220-4-2x.png/1200x630bb.png")),
            (Book(book_title = "Travel book", author = "CS", book_url = "https://images-na.ssl-images-amazon.com/images/I/51dHKuQkRPL._SX331_BO1,204,203,200_.jpg")),
            (Book(book_title = "ABC", author = "CS", book_url = "https://images-na.ssl-images-amazon.com/images/I/81H4+Wn-iLL.jpg"))
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BookList)
            adapter = BooksAdapter(books)
            addItemDecoration(DividerItemDecoration(this@BookList, LinearLayoutManager.VERTICAL))
        }

        val swipeHandler = object : SwipeToDeleteCallback(this) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as BooksAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        addBtn.setOnClickListener(){
            startActivity(Intent(this, AddBook::class.java))
        }

        logoutBtn.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}
