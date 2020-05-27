package com.example.bookstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.bookdetails_action_bar_layout.*

class BookDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val bookArray = intent.getParcelableArrayListExtra<Book>("EXTRA_bookArray")
        var arrStatusNew : Int
        arrStatusNew = intent.getIntExtra("EXTRA_arrStatus",3)

        val adapterPos = intent.getIntExtra("EXTRA_adapterPosition", 0)

        //Appbar title changes according to book title
        val currentBook = bookArray[adapterPos]
        val tv1 : TextView = findViewById(R.id.book_title)
        tv1.text = currentBook.book_title

        //TODO: Fetch book details into each respective field


        //TODO: Edit button change to Done button once activated

        backBtn.setOnClickListener {
            val intent = Intent(this, BookList::class.java)
            arrStatusNew = 3
            intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
            intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)
            startActivity(intent)
            finish()
        }
    }
}
