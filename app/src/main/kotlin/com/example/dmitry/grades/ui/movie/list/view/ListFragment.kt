package com.example.dmitry.grades.ui.movie.list.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.dmitry.grades.R
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.domain.models.entity.Movie
import com.example.dmitry.grades.ui.RemoteScopeFactory
import com.example.dmitry.grades.ui.base.observers.LoadingObserver
import com.example.dmitry.grades.ui.base.ui.errors.ErrorHandler
import com.example.dmitry.grades.ui.base.ui.errors.LoadingView
import com.example.dmitry.grades.ui.base.ui.fragment.DIFragment
import com.example.dmitry.grades.ui.movie.MovieRouter
import com.example.dmitry.grades.ui.movie.list.FilterType
import com.example.dmitry.grades.ui.movie.list.ListViewModel
import toothpick.Toothpick


class ListFragment : DIFragment(), LoadingView {

    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView

    private lateinit var refreshLayout: SwipeRefreshLayout

    private lateinit var viewModel: ListViewModel

    private lateinit var toolbar: Toolbar

    private var movieRouter: MovieRouter? = null

    private val factory = Toothpick.openScope(Scopes.REMOTE_SCOPE).getInstance(RemoteScopeFactory::class.java)

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_grid, container, false)
        refreshLayout = rootView.findViewById(R.id.refresh)
        recyclerView = rootView.findViewById(R.id.recycler)
        toolbar = rootView.findViewById(R.id.toolbar)

        viewModel = ViewModelProviders.of(this, factory).get(ListViewModel::class.java)
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

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
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

    override fun showLoading() {
        refreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        refreshLayout.isRefreshing = false
    }

}