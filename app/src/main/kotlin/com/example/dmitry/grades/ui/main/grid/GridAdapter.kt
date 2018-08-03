package com.example.dmitry.grades.ui.main.grid

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.dmitry.grades.R
import com.example.dmitry.grades.domain.models.Movie
import com.example.dmitry.grades.ui.main.MovieDiffUtils

class GridAdapter(private val clickListener: (Movie) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val FOOTER_TYPE = 1
        private const val DEFAULT_TYPE = 0
    }

    private val items: MutableList<Movie> = arrayListOf()

    private var _show: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FOOTER_TYPE) {
            LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_loading, parent, false))
        } else {
            val holder = GridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_grid, parent, false))
            holder.itemView.setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    clickListener(items[holder.adapterPosition])
                }
            }
            holder
        }
    }

    override fun getItemCount(): Int {
        return items.size + if (_show) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!isFooter(position)) {
            items[position].posterPath?.let {
                (holder as GridViewHolder).bind(it)
            }
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
        result.dispatchUpdatesTo(this)

    }

    fun isFooter(position: Int): Boolean {
        return items.size == position
    }

    fun setMoreItems(show: Boolean) {
        _show = show
        if (show) {
            notifyItemInserted(items.size)
        } else {
            notifyItemRemoved(items.size)
        }
    }
}