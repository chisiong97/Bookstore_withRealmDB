package com.example.bookstore

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Book(
    @PrimaryKey
    var id: Int? = null,
    var author: String? = null,
    var book_cover: String? = null,
    var book_desc: String? = null,
    var book_title: String? = null
):RealmObject()