package com.example.bookstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.addbook_action_bar_layout.*

class AddBook : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        var bookArray = intent.getParcelableArrayListExtra<Book>("EXTRA_bookArray")
        var arrStatusNew : Int
        arrStatusNew = intent.getIntExtra("EXTRA_arrStatus",1)

        doneBtn.setOnClickListener(){
            var bookTitle = book_title.text.toString()
            var bookAuthor = author.text.toString()
            var bookDesc = description.text.toString()

            //TODO: Get image loaded by user

            var newBook = Book(
                    author = bookAuthor,
                    book_title = bookTitle,
                    book_desc = bookDesc,
                    book_cover = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Empty_set.svg/400px-Empty_set.svg.png")

            bookArray.add(newBook)

            val intent = Intent(this, BookList::class.java)
            arrStatusNew = 2
            intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
            intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)
            startActivity(intent)
            finish()
        }

        backBtn.setOnClickListener(){
            val intent = Intent(this, BookList::class.java)
            arrStatusNew = 3
            intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
            intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)
            startActivity(intent)
            finish()
        }
    }
}
