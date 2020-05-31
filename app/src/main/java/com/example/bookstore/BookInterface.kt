package com.example.bookstore

import io.realm.Realm
import io.realm.RealmResults

interface BookInterface{

    fun addBook(realm: Realm, book: Book):Boolean
    fun removeBook(realm: Realm, id :Int):Boolean
    fun getBook (realm: Realm,id:Int): RealmResults<Book>?
    fun updateBook (realm: Realm,id: Int? , author: String?, cover:String?,desc:String?,title:String?):Boolean
}