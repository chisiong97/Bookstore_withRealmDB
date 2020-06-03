package com.example.bookstore

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_book_list.*
import kotlinx.android.synthetic.main.booklist_action_bar_layout.*
import io.realm.kotlin.createObject
import io.realm.kotlin.where


class BookList : AppCompatActivity()
{

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    val realm = Realm.getDefaultInstance()
    val results = realm.where<Book>().findAll()

    //Disable back
    override fun onBackPressed()
    {
        //super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)
        initUI()
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?)
    {
        println("Activity Result" + requestCode)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK)
            {

                //bookArray = results.toArray().toCollection(ArrayList()) as ArrayList<Book>


                viewAdapter.notifyDataSetChanged()
            }
            if (resultCode == Activity.RESULT_CANCELED)
            {
                //Write your code if there's no result
            }
        }
        if (requestCode == 2)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                viewAdapter.notifyDataSetChanged()

            }
            if (resultCode == Activity.RESULT_CANCELED)
            {
                //Write your code if there's no result
            }

        }

    }

    private fun initUI()
    {
        initRecyclerUI()

        btnAddBook.setOnClickListener()
        {
            val intent = Intent(this, AddBook::class.java)
            startActivityForResult(intent,2)
        }

        btnLogout.setOnClickListener()
        {
            //Delete all when logout
            realm.beginTransaction()
            realm.deleteAll()
            realm.commitTransaction()

            finish()
        }
    }

    private fun initRecyclerUI()
    {
        //Fetch data to array from db
        //bookArray = results.toArray().toCollection(ArrayList()) as ArrayList<Book>

        viewAdapter = BooksAdapter(results, this@BookList)

        //Fetch array to recyclerview
        bookRecycleView.apply {
            layoutManager = LinearLayoutManager(this@BookList)
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this@BookList, LinearLayoutManager.VERTICAL))
        }

        //Add swipe to delete into recyclerview
        val swipeHandler = object : SwipeToDeleteCallback(this)
        {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
            {
                val adapter = bookRecycleView.adapter as BooksAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(bookRecycleView)

    }

}

