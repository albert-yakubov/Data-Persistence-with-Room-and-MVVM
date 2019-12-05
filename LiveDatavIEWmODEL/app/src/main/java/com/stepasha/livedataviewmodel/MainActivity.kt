package com.stepasha.livedataviewmodel

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stepasha.livedataviewmodel.adapters.RecyclerAdapter
import com.stepasha.livedataviewmodel.database.MyDatabase
import com.stepasha.livedataviewmodel.model.Book
import com.stepasha.livedataviewmodel.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: RecyclerAdapter
    lateinit var viewModel: BookViewModel
    var list: MutableList<Book>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(this, list)
        recyclerView.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)
        viewModel.fetchAllData().observe(this, object : Observer<MutableList<Book>> {
            @SuppressLint("LogNotTimber")
            override fun onChanged(t: MutableList<Book>?) {
                Log.v("OnChanged","OnChanged!!")
                adapter.addItems(t)
            }
        })

        add.setOnClickListener {
            openDialog()
        }

    }
    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog)
        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        val submit = dialog.findViewById<View>(R.id.submit) as Button
        val name = dialog.findViewById<View>(R.id.name) as EditText
        val author = dialog.findViewById<View>(R.id.author) as EditText
        val genre = dialog.findViewById<View>(R.id.genre) as EditText

        submit.setOnClickListener {
            when {
                name.length() == 0 || author.length() == 0 || genre.length() == 0 ->
                    Toast.makeText(this@MainActivity, "Please fill all the fields"
                        , Toast.LENGTH_SHORT).show()

                else -> {
                    val book = Book(name.text.toString(), author.text.toString(), genre.text.toString())
                    MyDatabase.insertData(MyDatabase.getInstance(this), book)
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
        dialog.window?.attributes = lp
    }
}
