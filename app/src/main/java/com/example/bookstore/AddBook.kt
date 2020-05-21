package com.example.bookstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.addbook_action_bar_layout.*

class AddBook : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        doneBtn.setOnClickListener(){
            startActivity(Intent(this, BookList::class.java))
            //TODO: Save book to db
            finish()
        }

        backBtn.setOnClickListener(){
            startActivity(Intent(this, BookList::class.java))
            //TODO: Alert confirm quit? unsaved data will lost
            finish()
        }
    }
}
