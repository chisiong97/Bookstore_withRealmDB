package com.example.bookstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.bookdetails_action_bar_layout.*

class BookDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        //TODO: Appbar title changes according to book title

        //TODO: Edit button change to Done button once activated

        backBtn.setOnClickListener(){
            val intent = Intent(this, BookList::class.java)
            /*
            arrStatusNew = 3
            intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
            intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)

             */
            startActivity(intent)
            finish()
        }
    }
}
