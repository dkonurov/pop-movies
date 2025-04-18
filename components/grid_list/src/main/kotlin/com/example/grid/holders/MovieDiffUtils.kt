package com.example.grid.holders

import androidx.recyclerview.widget.DiffUtil
import com.example.core.storage.db.entity.LocalMovie

class MovieDiffUtils(
    private val oldList: List<LocalMovie>,
    private val newList: List<LocalMovie>,
) : DiffUtil.Callback() {
    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean = oldList[oldItemPosition] === newList[newItemPosition]

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean = oldList[oldItemPosition] == newList[newItemPosition]
}