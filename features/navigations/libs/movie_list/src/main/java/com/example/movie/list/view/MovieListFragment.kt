package com.example.movie.list.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.example.grid.MovieListAdapter
import com.example.grid.recycler.MovieListScrollListener
import com.example.grid.recycler.SpanSizeLookup
import com.example.movie.R
import com.example.movie.di.MovieListModule
import com.example.movie.di.MovieListScope
import com.example.movie.list.ListViewModel
import com.example.movie.list.view.widget.FilterPopupMenu
import kotlinx.android.synthetic.main.fragment_grid.recycler
import kotlinx.android.synthetic.main.fragment_grid.refresh
import kotlinx.android.synthetic.main.fragment_grid.toolbar
import toothpick.Toothpick

class MovieListFragment : DIFragment(), LoadingView {

    companion object {
        fun newInstance(): MovieListFragment {
            return MovieListFragment()
        }
    }

    private lateinit var viewModel: ListViewModel

    private var movieRouter: MovieRouter? = null

    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun createScope() {
        Toothpick.openScopes(BottomNavigationCoreScope.NAME, MovieListScope.NAME)
                .installModules(MovieListModule())
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
        return inflater.inflate(R.layout.fragment_grid, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_movie, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.filter -> showPopupFilter()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showPopupFilter(): Boolean {
        val activity = requireActivity()
        val popup = FilterPopupMenu(activity, activity.findViewById(R.id.filter))
        popup.show()
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieListAdapter(requireContext()) { movie: Movie ->
            movieRouter?.showDetails(movie.id)
        }
        viewModel = Toothpick.openScope(MovieListScope.NAME)
                .getInstance(ListViewModel::class.java)
        compatActivity?.let {
            it.setSupportActionBar(toolbar)
            it.setTitle(R.string.list_title)
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

    override fun showLoading() {
        refresh.isRefreshing = true
    }

    override fun hideLoading() {
        refresh.isRefreshing = false
    }

}