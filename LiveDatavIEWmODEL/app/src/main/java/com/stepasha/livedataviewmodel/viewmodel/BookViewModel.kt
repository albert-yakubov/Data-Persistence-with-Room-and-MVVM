package com.stepasha.livedataviewmodel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stepasha.livedataviewmodel.database.MyDatabase
import com.stepasha.livedataviewmodel.model.Book

class BookViewModel(application: Application) : AndroidViewModel(application) {
    var list: LiveData<MutableList<Book>> = MyDatabase.getData(MyDatabase.getInstance(this.getApplication()))

    fun fetchAllData() : LiveData<MutableList<Book>> = list
}