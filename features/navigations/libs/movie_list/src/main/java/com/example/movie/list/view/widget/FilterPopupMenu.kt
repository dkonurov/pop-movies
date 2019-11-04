package com.example.movie.list.view.widget

import android.view.View
import android.widget.PopupMenu
import com.example.base.extensions.viewModel
import com.example.bottom.navigation.ui.models.FilterType
import com.example.movie.R
import com.example.movie.list.ListViewModel
import com.example.movie.list.view.MovieListFragment

internal class FilterPopupMenu(fragment: MovieListFragment, anchorView: View) :
    PopupMenu(fragment.requireContext(), anchorView) {

    init {
        val viewModel = fragment.viewModel {
            fragment.getScope().getInstance(ListViewModel::class.java)
        }

        menuInflater.inflate(R.menu.filter_list, menu)
        setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.popularity -> viewModel.filter(FilterType.POPULARITY)
                R.id.releaseDate -> viewModel.filter(FilterType.RELEASE_DATE)
                R.id.voteCount -> viewModel.filter(FilterType.VOTE_COUNT)
            }
            true
        }
    }
}