package com.example.grid

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val image: ImageView = itemView as ImageView

    fun bind(poster: String?) {
        if (poster != null) {
            Glide.with(image)
                    .load(poster)
                    .into(image)
        } else {
            image.setImageBitmap(null)
        }

    }
}