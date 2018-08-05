package com.example.dmitry.grades.ui.movie.list.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val _image: ImageView = itemView as ImageView

    fun bind(poster: String?) {
        if (poster != null) {
            Glide.with(_image)
                    .load(poster)
                    .into(_image)
        } else {
            _image.setImageBitmap(null)
        }

    }
}