package com.example.bookstore

import android.app.Activity
import android.app.PendingIntent.getActivity
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
        initUI()
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                initRecyclerUI()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private fun initUI(){
        initRecyclerUI()

        btnAddBook.setOnClickListener(){
            val intent = Intent(this, AddBook::class.java)
            startActivityForResult(intent,1)
        }

        btnLogout.setOnClickListener(){
            finish()
        }
    }

    private fun initRecyclerUI(){
        //Fetch data to array from db
        val realm = Realm.getDefaultInstance()

        val results = realm.where<Book>().findAll()

        bookArray = results.toArray().toCollection(ArrayList()) as ArrayList<Book>

        //Fetch array to recyclerview
        bookRecycleView.apply {
            layoutManager = LinearLayoutManager(this@BookList)
            adapter = BooksAdapter(bookArray, this@BookList)
            addItemDecoration(DividerItemDecoration(this@BookList, LinearLayoutManager.VERTICAL))
        }

        //Add swipe to delete into recyclerview
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = bookRecycleView.adapter as BooksAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(bookRecycleView)

    }

}

