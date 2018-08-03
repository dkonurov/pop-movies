package com.example.dmitry.grades.ui.main.grid

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val _image: ImageView = itemView as ImageView

    fun bind(poster: String) {
        Glide.with(_image)
                .load(poster)
                .into(_image)
    }
}