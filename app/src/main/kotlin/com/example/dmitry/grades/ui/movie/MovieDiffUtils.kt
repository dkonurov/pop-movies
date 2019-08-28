package com.example.dmitry.grades.ui.movie

import androidx.recyclerview.widget.DiffUtil
import com.example.core.models.entity.Movie

class MovieDiffUtils(private val oldList: List<Movie>, private val newList: List<Movie>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}