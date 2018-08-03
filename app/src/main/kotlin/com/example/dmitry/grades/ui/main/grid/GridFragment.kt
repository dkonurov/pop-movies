package com.example.dmitry.grades.ui.main.grid

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dmitry.grades.R
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.domain.models.Movie
import toothpick.Toothpick


class GridFragment : Fragment() {

    companion object {
        fun newInstance(): GridFragment {
            return GridFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView

    private lateinit var refreshLayout: SwipeRefreshLayout

    private lateinit var viewModel: GridViewModel

    private val factory = Toothpick.openScope(Scopes.REMOTE_SCOPE).getInstance(GridViewModelFactory::class.java)

    private val adapter = GridAdapter { movie: Movie ->
        Toast.makeText(context, movie.id.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_grid, container, false)
        refreshLayout = rootView.findViewById(R.id.refresh)
        recyclerView = rootView.findViewById(R.id.recycler)
        viewModel = ViewModelProviders.of(this, factory).get(GridViewModel::class.java)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.getChildCount()
                val totalItemCount = layoutManager.getItemCount()
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    viewModel.loadMore()
                }
            }
        }

        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)

        refreshLayout.setOnRefreshListener {
            viewModel.forceLoad()
        }
        initViewModel(savedInstanceState)
    }

    private fun initViewModel(savedInstanceState: Bundle?) {
        viewModel.movies.observe(this, Observer {
            adapter.setData(it)
        })
        viewModel.progress.observe(this, Observer {
            it?.let {
                refreshLayout.isRefreshing = it
            }
        })
        if (savedInstanceState == null) {
            viewModel.load()
        }
    }

}