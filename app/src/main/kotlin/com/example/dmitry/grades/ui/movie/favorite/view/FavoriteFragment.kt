package com.example.dmitry.grades.ui.movie.favorite.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.ui.observers.LoadingObserver
import com.example.base.ui.ui.errors.ErrorHandler
import com.example.base.ui.ui.errors.LoadingView
import com.example.base.ui.ui.fragment.DIFragment
import com.example.core.models.entity.Movie
import com.example.dmitry.grades.R
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.ui.ApplicationScopeFactory
import com.example.dmitry.grades.ui.movie.MovieRouter
import com.example.dmitry.grades.ui.movie.favorite.FavoriteViewModel
import com.example.dmitry.grades.ui.movie.list.FilterType
import com.example.dmitry.grades.ui.movie.list.view.ListAdapter
import toothpick.Toothpick

class FavoriteFragment : DIFragment(), LoadingView {

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView

    private lateinit var refreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout

    private lateinit var viewModel: FavoriteViewModel

    private lateinit var toolbar: Toolbar

    private var movieRouter: MovieRouter? = null

    private val factory = Toothpick.openScope(Scopes.REMOTE_SCOPE)
            .getInstance(ApplicationScopeFactory::class.java)

    private val adapter = ListAdapter { movie: Movie ->
        movieRouter?.let {
            it.showDetails(movie.id)
        }
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
        refreshLayout = rootView.findViewById(R.id.refresh)
        recyclerView = rootView.findViewById(R.id.recycler)
        toolbar = rootView.findViewById(R.id.toolbar)

        viewModel = ViewModelProviders.of(this, factory).get(FavoriteViewModel::class.java)
        return rootView
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
        activity?.let {
            val popup = PopupMenu(it, it.findViewById(R.id.filter))
            popup.menuInflater.inflate(R.menu.filter_list, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.popularity -> viewModel.filter(FilterType.POPULARITY)
                    R.id.releaseDate -> viewModel.filter(FilterType.RELEASE_DATE)
                    R.id.voteCount -> viewModel.filter(FilterType.VOTE_COUNT)
                }
                true
            }
            popup.show()
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compatActivity?.let {
            it.setSupportActionBar(toolbar)
            it.setTitle(R.string.list_title)
        }
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.isFooter(position)) layoutManager.spanCount else 1
            }
        }
        recyclerView.layoutManager = layoutManager
        val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= 4) {
                    viewModel.loadMore()
                }
            }
        }

        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)

        refreshLayout.setOnRefreshListener {
            viewModel.forceLoad()
        }
        initViewModel()
    }

    override fun showLoading() {
        refreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        refreshLayout.isRefreshing = false
    }

    private fun initViewModel() {
        viewModel.movies.observe(this, Observer {
            adapter.setData(it)
        })
        viewModel.loading.observe(this, LoadingObserver(this))
        viewModel.moreMovies.observe(this, Observer { it ->
            it?.let {
                adapter.setMoreItems(it)
            }
        })
        ErrorHandler.handleError(viewModel, this)
        if (viewModel.movies.value == null) {
            viewModel.load()
        }
    }

}