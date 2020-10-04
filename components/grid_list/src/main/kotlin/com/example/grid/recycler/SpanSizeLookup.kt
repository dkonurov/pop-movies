package com.example.grid.recycler

import androidx.recyclerview.widget.GridLayoutManager

class SpanSizeLookup(
    private val layoutManager: GridLayoutManager,
    private val isFooter: (Int) -> Boolean
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int =
        if (isFooter.invoke(position)) layoutManager.spanCount else 1
}