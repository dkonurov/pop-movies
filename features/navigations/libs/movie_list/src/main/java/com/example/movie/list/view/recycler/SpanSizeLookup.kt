package com.example.movie.list.view.recycler

import androidx.recyclerview.widget.GridLayoutManager
import com.example.grid.MovieListAdapter

internal class SpanSizeLookup(
    private val adapter: MovieListAdapter,
    private val layoutManager: GridLayoutManager
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int =
            if (adapter.isFooter(position)) layoutManager.spanCount else 1
}