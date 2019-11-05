package com.example.myUol.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myUol.R
import com.example.myUol.model.Points
import kotlinx.android.synthetic.main.text_row_item.view.*


class MainAdapter(private val items: List<Points>, private val listener: (Points) -> Unit) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_row_item, parent, false)

        return MainViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = items[position]
        val view = holder.itemView
        with( view ) {
            tv_wifi.text = item.Descricao
            distance.text = String.format("%.2f", item.distance) + " KM"
            itemClick.setOnClickListener { listener(item) }

        }
    }

    override fun getItemCount(): Int = items.size

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view)

}

