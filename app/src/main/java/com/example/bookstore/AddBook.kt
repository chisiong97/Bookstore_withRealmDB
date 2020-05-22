package com.example.bookstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.addbook_action_bar_layout.*

class AddBook : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        var bookArray : ArrayList<Book>? = intent.getParcelableArrayListExtra("EXTRA_bookArray")

        doneBtn.setOnClickListener(){
            var bookTitle = book_title.text.toString()
            var bookAuthor = author.text.toString()
            var bookDesc = description.text.toString()

            //TODO: Get image loaded by user

            var newBook = Book(
                    author = bookAuthor,
                    book_title = bookTitle,
                    book_desc = bookDesc,
                    book_cover = "")

            val intent = Intent(this, BookList::class.java)
            intent.putExtra("EXTRA_NEW_BOOK", newBook)
            startActivity(intent)
            finish()
        }

        backBtn.setOnClickListener(){
            startActivity(Intent(this, BookList::class.java))
            //TODO: Alert confirm quit? unsaved data will lost
            finish()
        }
    }
}
