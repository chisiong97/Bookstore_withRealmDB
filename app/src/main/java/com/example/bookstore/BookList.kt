package com.example.bookstore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_book_list.*
import kotlinx.android.synthetic.main.booklist_action_bar_layout.*
import io.realm.kotlin.createObject
import io.realm.kotlin.where


class BookList : AppCompatActivity() {

    var bookArray = arrayListOf<Book>()

    private val helper = BookModel()

    //Disable back
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)
        Realm.init(this)

        //Get result from db
        val realm = Realm.getDefaultInstance()
        val results = realm.where<Book>().findAll()
        bookArray = results.toArray().toCollection(ArrayList()) as ArrayList<Book>

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BookList)
            adapter = BooksAdapter(bookArray)
            addItemDecoration(DividerItemDecoration(this@BookList, LinearLayoutManager.VERTICAL))
        }

        //Add swipe to delete into recyclerview
        val swipeHandler = object : SwipeToDeleteCallback(this) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as BooksAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //Action bar buttons onclicklistener
        btnAddBook.setOnClickListener(){
            val intent = Intent(this, AddBook::class.java)
            startActivity(intent)
            finish()
        }

        btnLogout.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

}

