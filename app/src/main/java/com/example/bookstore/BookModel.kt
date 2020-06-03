package com.example.bookstore

import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import com.example.bookstore.Book as Book

class BookModel : BookInterface
{
    override fun addBook(realm: Realm, book: Book): Boolean
    {
        return try
        {
            realm.beginTransaction()

            val currentIdNum: Number? = realm.where(Book::class.java).max("id")

            val nextId: Int

            nextId = if (currentIdNum == null)
            {
                0
            }
            else
            {
                currentIdNum.toInt() + 1
            }

            book.id = nextId

            realm.copyToRealmOrUpdate(book)

            realm.commitTransaction()

            true
        }
        catch (e: Exception)
        {
            println(e)
            false
        }
    }

    override fun removeBook(realm: Realm, id:Int): Boolean
    {
        return try
        {
            realm.beginTransaction()
            realm.where(Book ::class.java).equalTo("id", id).findFirst()?.deleteFromRealm()
            realm.commitTransaction()
            true
        }
        catch (e: Exception)
        {
            println(e)
            return false
        }
    }

    override fun getBook(realm: Realm, id:Int): RealmResults<Book>?
    {

        val query = realm.where<Book>()
        query.equalTo("id",id)

        return query.findAll()

    }

    override fun updateBook(realm: Realm, id: Int? , author: String?, cover:String?,desc:String?,title:String?): Boolean
    {
        return try
        {

            realm.executeTransactionAsync(Realm.Transaction { bgRealm ->
                // Find a dog to update.
                val currentBook = bgRealm.where<Book>().equalTo("id", id).findFirst()!!
                currentBook.author = author
                currentBook.book_cover = cover
                currentBook.book_desc = desc
                currentBook.book_title = title
            }, Realm.Transaction.OnSuccess {
                // Original queries and Realm objects are automatically updated.

            })

            true
        }
        catch (e:Exception)
        {
            println(e)
            return false
        }
    }
}