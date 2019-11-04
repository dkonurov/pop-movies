package com.example.dmitry.grades.ui.movie.list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core.models.entity.Movie
import com.example.dmitry.grades.R
import com.example.dmitry.grades.ui.base.ui.LoadingViewHolder
import com.example.dmitry.grades.ui.movie.MovieDiffUtils

class ListAdapter(private val clickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val FOOTER_TYPE = 1
        private const val DEFAULT_TYPE = 0
    }

    private val items: MutableList<Movie> = arrayListOf()

    private var _show: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FOOTER_TYPE) {
            LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_loading,
                    parent,
                    false
                )
            )
        } else {
            val holder = GridViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_grid,
                    parent,
                    false
                )
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
        return items.size + if (_show) 1 else 0
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