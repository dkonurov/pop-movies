package com.example.favorite.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.base.extensions.viewModel
import com.example.base.ui.observers.LoadingObserver
import com.example.base.ui.ui.errors.ErrorHandler
import com.example.base.ui.ui.errors.LoadingView
import com.example.base.ui.ui.fragment.DIFragment
import com.example.core.models.entity.LocalMovie
import com.example.favorite.di.FavoriteListModule
import com.example.favorite.list.FavoriteViewModel
import com.example.favorite.list.R
import com.example.grid.MovieListAdapter
import com.example.grid.recycler.MovieListScrollListener
import com.example.grid.recycler.SpanSizeLookup
import kotlinx.android.synthetic.main.fragment_grid.*
import toothpick.config.Module

class FavoriteFragment : DIFragment(), LoadingView {

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    private lateinit var adapter: MovieListAdapter

    override fun getModules(): Array<Module>? {
        return arrayOf(FavoriteListModule())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_grid, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = viewModel { getScope().getInstance(FavoriteViewModel::class.java) }
        adapter = MovieListAdapter(requireContext()) { movie: LocalMovie ->
            viewModel.showDetails(movie.id)
        }

        compatActivity?.let {
            it.setSupportActionBar(toolbar)
            it.setTitle(R.string.favorite_title)
        }
        recycler.adapter = adapter
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = SpanSizeLookup(layoutManager, adapter::isFooter)
        recycler.layoutManager = layoutManager
        recycler.addOnScrollListener(MovieListScrollListener(layoutManager, viewModel::loadMore))

        refresh.setOnRefreshListener {
            viewModel.forceLoad()
        }
        initViewModel(viewModel)
    }

    override fun showLoading() {
        refresh.isRefreshing = true
    }

    override fun hideLoading() {
        refresh.isRefreshing = false
    }

    private fun initViewModel(viewModel: FavoriteViewModel) {
        viewModel.movies.observe(
            this,
            {
                adapter.setData(it)
            }
        )
        viewModel.loading.observe(this, LoadingObserver(this))
        ErrorHandler.handleError(viewModel, this)
        if (viewModel.movies.value == null) {
            viewModel.load()
        }
    }
}