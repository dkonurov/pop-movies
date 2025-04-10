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
import com.example.base.ui.ui.fragment.ToothpickFragment
import com.example.core.storage.db.entity.LocalMovie
import com.example.dmitry.grades.features.libs.favorite_list.R
import com.example.dmitry.grades.features.libs.favorite_list.databinding.FragmentGridBinding
import com.example.favorite.di.FavoriteListModule
import com.example.favorite.list.FavoriteViewModel
import com.example.grid.MovieListAdapter
import com.example.grid.recycler.MovieListScrollListener
import com.example.grid.recycler.SpanSizeLookup
import toothpick.config.Module

class FavoriteFragment : ToothpickFragment(), LoadingView {

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    private lateinit var adapter: MovieListAdapter

    private var binding: FragmentGridBinding? = null

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
        val binding = FragmentGridBinding.bind(view)
        val viewModel = viewModel { getScope().getInstance(FavoriteViewModel::class.java) }
        adapter = MovieListAdapter(requireContext()) { movie: LocalMovie ->
            viewModel.showDetails(movie.id)
        }

        compatActivity?.let {
            it.setSupportActionBar(binding.toolbar)
            it.setTitle(R.string.favorite_title)
        }
        binding.recycler.adapter = adapter
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = SpanSizeLookup(layoutManager, adapter::isFooter)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.addOnScrollListener(MovieListScrollListener(layoutManager, viewModel::loadMore))

        binding.refresh.setOnRefreshListener {
            viewModel.forceLoad()
        }
        initViewModel(viewModel)
        this.binding = binding
    }

    override fun showLoading() {
        binding?.refresh?.isRefreshing = true
    }

    override fun hideLoading() {
        binding?.refresh?.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }

    private fun initViewModel(viewModel: FavoriteViewModel) {
        viewModel.movies.observe(
            viewLifecycleOwner,
            {
                adapter.setData(it)
            }
        )
        viewModel.loading.observe(viewLifecycleOwner, LoadingObserver(this))
        ErrorHandler.handleError(viewModel, this)
        if (viewModel.movies.value == null) {
            viewModel.load()
        }
    }
}