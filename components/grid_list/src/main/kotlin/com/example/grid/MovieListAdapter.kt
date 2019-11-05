package com.example.grid

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core.models.entity.Movie
import com.example.grid.holders.LoadingViewHolder
import com.example.grid.holders.MovieDiffUtils

class MovieListAdapter(
    context: Context,
    private val clickListener: (Movie) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val FOOTER_TYPE = 1
        private const val DEFAULT_TYPE = 0
    }

    private val layoutInflater = LayoutInflater.from(context)

    private val items: MutableList<Movie> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FOOTER_TYPE) {
            LoadingViewHolder(
                layoutInflater.inflate(R.layout.list_item_loading, parent, false)
            )
        } else {
            val holder = GridViewHolder(
                layoutInflater.inflate(R.layout.list_item_grid, parent, false)
            )
            holder.itemView.setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    clickListener(items[holder.adapterPosition])
                }
            }
            holder
        }
    }

    override fun getItemCount(): Int {
        val size = items.size
        return size + if (size > 0) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!isFooter(position)) {
            (holder as GridViewHolder).bind(items[position].posterPath)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isFooter(position)) FOOTER_TYPE else DEFAULT_TYPE
    }

    fun setData(movies: List<Movie>?) {
        val saveMovies = movies ?: emptyList()
        val result = DiffUtil.calculateDiff(MovieDiffUtils(items, saveMovies))
        items.clear()
        items.addAll(saveMovies)
        if (saveMovies.isEmpty()) {
            notifyDataSetChanged()
        } else {
            result.dispatchUpdatesTo(this)
        }
    }

    fun isFooter(position: Int): Boolean {
        return items.size == position
    }
}