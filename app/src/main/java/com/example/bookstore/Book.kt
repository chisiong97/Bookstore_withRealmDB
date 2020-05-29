package com.example.bookstore

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book (
    var author: String,
    var book_cover : Uri,
    var book_desc : String,
    var book_title: String
):Parcelable