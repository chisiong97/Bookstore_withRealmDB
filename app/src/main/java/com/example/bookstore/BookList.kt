package com.example.bookstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_book_list.*


class BookList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        //TODO: To use DB to populate
        //Note: user upload images..not url
        var books = mutableListOf<Book>(
            (Book(book_title = "Programming", author = "CS", book_url = "https://images-na.ssl-images-amazon.com/images/I/514axA2lwpL.jpg")),
            (Book(book_title = "Dictionary", author = "CS", book_url = "https://is1-ssl.mzstatic.com/image/thumb/Purple123/v4/49/98/2f/49982f6e-a96e-3b94-95bf-bcbb05dd5c5e/AppIcon-0-85-220-4-2x.png/1200x630bb.png")),
            (Book(book_title = "Travel book", author = "CS", book_url = "https://images-na.ssl-images-amazon.com/images/I/51dHKuQkRPL._SX331_BO1,204,203,200_.jpg")),
            (Book(book_title = "ABC", author = "CS", book_url = "https://images-na.ssl-images-amazon.com/images/I/81H4+Wn-iLL.jpg"))
        )

        /*
        for (i in 0 until 50){
            books.add(Book(book_title = "ABC", author = "CS", book_url = "https://images-na.ssl-images-amazon.com/images/I/81H4+Wn-iLL.jpg"))
        }
        */

        recyclerView.addItemDecoration(DividerItemDecoration(this@BookList, LinearLayoutManager.VERTICAL))


        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BookList)
            adapter = BooksAdapter(books)

        }
    }
}
