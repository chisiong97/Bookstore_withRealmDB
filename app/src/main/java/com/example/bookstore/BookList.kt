package com.example.bookstore

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_book_list.*
import kotlinx.android.synthetic.main.booklist_action_bar_layout.*


class BookList : AppCompatActivity() {

    //1 = init ori array , 2 = load latest updated array, 3 = load last updated array(no new book added)
    var arrStatus = 1
    var bookArray = arrayListOf<Book>()

    //Disable back
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        arrStatus = intent.getIntExtra("EXTRA_UpdatedStatus", 1)

        //TODO: Change book_cover attributes to URI
        if (arrStatus==1){
            //Init array
            bookArray.add(
                (Book(
                    author = "CS",
                    book_title = "Programming",
                    book_desc = "This is a random description.",
                    book_cover = "https://images-na.ssl-images-amazon.com/images/I/514axA2lwpL.jpg")
                        )
            )

            bookArray.add(
                (Book(
                    author = "CS",
                    book_title = "Dictionary",
                    book_desc = "This is a dictionary description.",
                    book_cover = "https://is1-ssl.mzstatic.com/image/thumb/Purple123/v4/49/98/2f/" +
                            "49982f6e-a96e-3b94-95bf-bcbb05dd5c5e/AppIcon-0-85-220-4-2x.png/1200x630bb.png")
                        )
            )

        }else {
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            bookArray = intent.getParcelableArrayListExtra("EXTRA_UpdatedBookArray")
        }


        val LOAD = "Test"
        //Log.d(LOAD, newBook.toString())
        Log.d(LOAD, bookArray.toString())

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BookList)
            adapter = BooksAdapter(bookArray,arrStatus)
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
        addBtn.setOnClickListener(){
            val intent = Intent(this, AddBook::class.java)
            intent.putExtra("EXTRA_arrStatus", arrStatus)
            intent.putParcelableArrayListExtra("EXTRA_bookArray", bookArray)
            Log.d(LOAD, bookArray.toString())

            startActivity(intent)
            finish()
        }

        logoutBtn.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

}

