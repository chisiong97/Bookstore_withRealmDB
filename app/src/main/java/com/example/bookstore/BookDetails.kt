package com.example.bookstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_NULL
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_book_details.*
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
        val title : EditText = findViewById(R.id.book_title)
        val author : EditText = findViewById(R.id.author)
        val desc : EditText = findViewById(R.id.description)

        title.setText(currentBook.book_title)
        author.setText(currentBook.author)
        desc.setText(currentBook.book_desc)
        imageView.setImageURI(currentBook.book_cover)

        //Disable text field editable
        title.inputType = TYPE_NULL
        author.inputType = TYPE_NULL
        desc.inputType = TYPE_NULL

        //Back button
        backBtn.setOnClickListener {
            val intent = Intent(this, BookList::class.java)
            arrStatusNew = 3
            intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
            intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)
            startActivity(intent)
            finish()
        }

        //Edit button
        editBtn.setOnClickListener {
            //Edit button change to Done button once activated
            if (editBtn.text == "Done"){
                //Update book arraylist
                currentBook.book_title = title.text.toString()
                println("Test current" + currentBook.book_title)
                currentBook.author = author.text.toString()
                currentBook.book_desc = desc.text.toString()

                val intent = Intent(this, BookList::class.java)
                arrStatusNew = 2
                intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
                intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)
                startActivity(intent)
                finish()

            }else{
                editBtn.text = "Done"
                //Set text field editable
                title.inputType = TYPE_CLASS_TEXT
                author.inputType = TYPE_CLASS_TEXT
                desc.inputType = TYPE_CLASS_TEXT

            }

        }
    }
}
