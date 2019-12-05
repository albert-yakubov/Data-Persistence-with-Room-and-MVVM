package com.stepasha.livedataviewmodel.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.stepasha.livedataviewmodel.model.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM Book")
    fun getBookInfo() : LiveData<MutableList<Book>>

    @Insert
    fun addBook(book : Book)

    @Update(onConflict = REPLACE)
    fun updateBook( book: Book)

    @Delete
    fun deleteBook( book: Book?)
}