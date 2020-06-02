package com.example.bookstore

import android.app.Application

class MyApplication : Application(){
    companion object{
        var globalArr = arrayListOf<Book>()
    }
}