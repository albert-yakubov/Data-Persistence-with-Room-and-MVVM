package com.stepasha.livedataviewmodel.database

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stepasha.livedataviewmodel.model.Book

@Database(entities = [Book::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "Books.db").build()
            }
            return INSTANCE as MyDatabase
        }

        @SuppressLint("StaticFieldLeak")
        fun insertData(mydata: MyDatabase, book: Book) {
            object : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg voids: Void): Void? {
                    mydata.bookDao().addBook(book)
                    return null
                }
            }.execute()
        }

        @SuppressLint("StaticFieldLeak")
        fun getData(mydata: MyDatabase): LiveData<MutableList<Book>> {
            lateinit var lists: LiveData<MutableList<Book>>

            return object : AsyncTask<Void, Void, LiveData<MutableList<Book>>>() {
                override fun doInBackground(vararg voids: Void): LiveData<MutableList<Book>>? {
                    lists = mydata.bookDao().getBookInfo()
                    return lists
                }
            }.execute().get()
        }

        @SuppressLint("StaticFieldLeak")
        fun deleteData(mydata: MyDatabase, book: Book?) {
            object : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg voids: Void): Void? {
                    mydata.bookDao().deleteBook(book)
                    return null
                }
            }.execute()
        }
    }
}