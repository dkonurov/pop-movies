package com.example.dmitry.grades.ui.main.grid

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.dmitry.grades.R
import com.example.dmitry.grades.domain.models.Movie

class GridAdapter(private val clickListener: (Movie) -> Unit) : RecyclerView.Adapter<GridViewHolder>() {

    private val items: MutableList<Movie> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val holder = GridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_grid, parent, false))
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                clickListener(items[holder.adapterPosition])
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        items[position].posterPath?.let {
            holder.bind(it)
        }
    }

    fun setData(movies: List<Movie>?) {
        items.clear()
        movies?.let {
            items.addAll(it)
        }
        notifyDataSetChanged()
    }
}