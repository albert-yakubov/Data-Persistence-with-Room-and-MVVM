package com.stepasha.livedataviewmodel.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(var bookName : String, var author : String,
                var genre : String){
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null
}