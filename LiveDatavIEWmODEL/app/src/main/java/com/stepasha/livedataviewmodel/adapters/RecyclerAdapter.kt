package com.stepasha.livedataviewmodel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stepasha.livedataviewmodel.R
import com.stepasha.livedataviewmodel.database.MyDatabase
import com.stepasha.livedataviewmodel.model.Book
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecyclerAdapter(val context: Context, var data : MutableList<Book>?) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {




    override fun getItemCount(): Int = data?.size?:0

    class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){

        fun bindItems(book: Book?){
            itemView.name.setText(book?.bookName)
            itemView.author.setText(book?.author)
            itemView.genre.setText(book?.genre)
            itemView.setOnLongClickListener {
                MyDatabase.deleteData(MyDatabase.getInstance(itemView.context), book)
                true
            }
        }
    }

    fun addItems(t: MutableList<Book>?) {
        data = t
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItems(data?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
        return Holder(v)
    }
}