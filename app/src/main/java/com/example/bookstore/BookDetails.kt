package com.example.bookstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
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
        val tv1 : TextView = findViewById(R.id.book_title_appbar)
        tv1.text = currentBook.book_title

        //Fetch book details into each respective field
        var title : EditText = findViewById(R.id.book_title)
        var author : EditText = findViewById(R.id.author)
        var desc : EditText = findViewById(R.id.description)

        title.setText(currentBook.book_title)
        author.setText(currentBook.author)
        desc.setText(currentBook.book_desc)

        //TODO: Edit button change to Done button once activated

        backBtn.setOnClickListener {
            val intent = Intent(this, BookList::class.java)
            arrStatusNew = 3
            intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
            intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)
            startActivity(intent)
            finish()
        }

        editBtn.setOnClickListener {
            if (editBtn.text == "Done"){
                //TODO: Enable edit text and update book arraylist
            }else{
                editBtn.text = "Done"
            }

        }
    }
}
