package com.example.bookstore

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_book_list.*
import kotlinx.android.synthetic.main.booklist_action_bar_layout.*


class BookList : AppCompatActivity() {

    //Disable back
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        //Init array
        var bookArray = arrayListOf<Book> (
            (Book(
                author = "CS",
                book_title = "Programming",
                book_desc = "This is a random description.",
                book_cover = "https://images-na.ssl-images-amazon.com/images/I/514axA2lwpL.jpg")),
            (Book(
                author = "CS",
                book_title = "Dictionary",
                book_desc = "This is a dictionary description.",
                book_cover = "https://is1-ssl.mzstatic.com/image/thumb/Purple123/v4/49/98/2f/" +
                        "49982f6e-a96e-3b94-95bf-bcbb05dd5c5e/AppIcon-0-85-220-4-2x.png/1200x630bb.png"))
        )

        val LOAD = "Test"
        Log.d(LOAD, bookArray.toString())

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
        addBtn.setOnClickListener(){
            val intent = Intent(this, AddBook::class.java)
            /*
            intent.putParcelableArrayListExtra("EXTRA_bookArray", bookArray)
            Log.d(LOAD, bookArray.toString())

             */
            startActivity(intent)
            finish()
        }

        logoutBtn.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

}

