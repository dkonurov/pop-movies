package com.example.movie.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.base.extensions.viewModel
import com.example.base.ui.ui.errors.LoadingView
import com.example.base.ui.ui.fragment.DaggerFragment
import com.example.core.storage.db.entity.LocalMovie
import com.example.dmitry.grades.features.libs.movie_list.R
import com.example.dmitry.grades.features.libs.movie_list.databinding.FragmentGridBinding
import com.example.grid.MovieListAdapter
import com.example.grid.recycler.MovieListScrollListener
import com.example.grid.recycler.SpanSizeLookup
import com.example.movie.di.DaggerMovieListComponent
import com.example.movie.di.MovieListComponent
import com.example.movie.di.MovieListDependenciesImpl
import com.example.movie.list.ListMoviesUiState
import com.example.movie.list.ListViewModel
import com.example.movie.list.view.widget.FilterPopupMenu
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn

internal class MovieListFragment : DaggerFragment<MovieListComponent>(), LoadingView {

    public companion object Factory {
        fun newInstance(): Fragment {
            return MovieListFragment()
        }
    }

    override fun createComponent(): MovieListComponent {
        val dependencies = MovieListDependenciesImpl(getParentToothpickScope())
        return DaggerMovieListComponent.factory().create(dependencies)
    }

    private lateinit var viewModel: ListViewModel

    private lateinit var adapter: MovieListAdapter

    private var binding: FragmentGridBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> showPopupFilter()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showPopupFilter(): Boolean {
        val activity = requireActivity()
        val popup = FilterPopupMenu(this, activity.findViewById(R.id.filter))
        popup.show()
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentGridBinding.bind(view)
        viewModel = viewModel { getComponent().getListViewModel() }
        adapter = MovieListAdapter(requireContext()) { movie: LocalMovie ->
            viewModel.showDetails(movie.id)
        }

        compatActivity?.let {
            it.setSupportActionBar(binding.toolbar)
            it.setTitle(R.string.list_title)
        }
        binding.recycler.adapter = adapter
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = SpanSizeLookup(layoutManager, adapter::isFooter)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.addOnScrollListener(MovieListScrollListener(layoutManager, viewModel::loadMore))

        binding.refresh.setOnRefreshListener {
            viewModel.loadData()
        }
        initViewModel()
        this.binding = binding
    }

    private fun initViewModel() {
        viewModel.observe()
            .onEach {
                if (it is ListMoviesUiState.Loading) {
                    showLoading()
                } else {
                    hideLoading()
                }
                when (it) {
                    is ListMoviesUiState.Success -> {
                        adapter.setData(it.movies)
                    }

                    is ListMoviesUiState.Empty -> {
                        viewModel.loadData()
                    }

                    is ListMoviesUiState.Error -> {
                        showMessage(it.ui.message)
                    }

                    else -> {}
                }
            }
            .produceIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }

    override fun showLoading() {
        binding?.refresh?.isRefreshing = true
    }

    override fun hideLoading() {
        binding?.refresh?.isRefreshing = false
    }
}