package com.example.favorite.list.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.base.ui.observers.LoadingObserver
import com.example.base.ui.ui.errors.ErrorHandler
import com.example.base.ui.ui.errors.LoadingView
import com.example.base.ui.ui.fragment.DIFragment
import com.example.bottom.navigation.di.BottomNavigationCoreScope
import com.example.bottom.navigation.ui.MovieRouter
import com.example.core.models.entity.Movie
import com.example.favorite.di.FavoriteListModule
import com.example.favorite.di.FavoriteListScope
import com.example.favorite.list.FavoriteViewModel
import com.example.favorite.list.R
import com.example.grid.MovieListAdapter
import com.example.grid.recycler.MovieListScrollListener
import com.example.grid.recycler.SpanSizeLookup
import kotlinx.android.synthetic.main.fragment_grid.recycler
import kotlinx.android.synthetic.main.fragment_grid.refresh
import kotlinx.android.synthetic.main.fragment_grid.toolbar
import toothpick.Toothpick

class FavoriteFragment : DIFragment(), LoadingView {

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    private lateinit var viewModel: FavoriteViewModel

    private var movieRouter: MovieRouter? = null

    private lateinit var adapter: MovieListAdapter

    override fun createScope() {
        Toothpick.openScopes(BottomNavigationCoreScope.NAME, FavoriteListScope.NAME)
                .installModules(FavoriteListModule())
    }

    override fun closeScope() {
        Toothpick.closeScope(FavoriteListScope.NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        movieRouter = context as? MovieRouter
    }

    override fun onDetach() {
        super.onDetach()
        movieRouter = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_grid, container, false)
        adapter = MovieListAdapter(requireContext()) { movie: Movie ->
            movieRouter?.showDetails(movie.id)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = Toothpick.openScope(FavoriteListScope.NAME)
                .getInstance(FavoriteViewModel::class.java)
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
        initViewModel()
    }

    override fun showLoading() {
        refresh.isRefreshing = true
    }

    override fun hideLoading() {
        refresh.isRefreshing = false
    }

    private fun initViewModel() {
        viewModel.movies.observe(this, Observer {
            adapter.setData(it)
        })
        viewModel.loading.observe(this, LoadingObserver(this))
        ErrorHandler.handleError(viewModel, this)
        if (viewModel.movies.value == null) {
            viewModel.load()
        }
    }

}