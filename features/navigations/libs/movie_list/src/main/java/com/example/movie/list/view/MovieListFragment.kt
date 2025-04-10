package com.example.movie.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.base.extensions.viewModel
import com.example.base.ui.observers.LoadingObserver
import com.example.base.ui.ui.errors.ErrorHandler
import com.example.base.ui.ui.errors.LoadingView
import com.example.base.ui.ui.fragment.ToothpickFragment
import com.example.core.storage.db.entity.LocalMovie
import com.example.dmitry.grades.features.libs.movie_list.R
import com.example.dmitry.grades.features.libs.movie_list.databinding.FragmentGridBinding
import com.example.grid.MovieListAdapter
import com.example.grid.recycler.MovieListScrollListener
import com.example.grid.recycler.SpanSizeLookup
import com.example.movie.di.MovieListModule
import com.example.movie.list.ListViewModel
import com.example.movie.list.view.widget.FilterPopupMenu
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn
import toothpick.config.Module

class MovieListFragment : ToothpickFragment(), LoadingView {

    companion object {
        fun newInstance(): MovieListFragment {
            return MovieListFragment()
        }
    }

//    private lateinit var viewModel: ListViewModel
//
//    private lateinit var adapter: MovieListAdapter
//
//    private var binding: FragmentGridBinding? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }
//
//    override fun getModules(): Array<Module>? {
//        return arrayOf(MovieListModule())
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_grid, container, false)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.list_movie, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.filter -> showPopupFilter()
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    private fun showPopupFilter(): Boolean {
//        val activity = requireActivity()
//        val popup = FilterPopupMenu(this, activity.findViewById(R.id.filter))
//        popup.show()
//        return true
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val binding = FragmentGridBinding.bind(view)
//        viewModel = viewModel { getScope().getInstance(ListViewModel::class.java) }
//        adapter = MovieListAdapter(requireContext()) { movie: LocalMovie ->
//            viewModel.showDetails(movie.id)
//        }
//
//        compatActivity?.let {
//            it.setSupportActionBar(binding.toolbar)
//            it.setTitle(R.string.list_title)
//        }
//        binding.recycler.adapter = adapter
//        val layoutManager = GridLayoutManager(context, 2)
//        layoutManager.spanSizeLookup = SpanSizeLookup(layoutManager, adapter::isFooter)
//        binding.recycler.layoutManager = layoutManager
//        binding.recycler.addOnScrollListener(MovieListScrollListener(layoutManager, viewModel::loadMore))
//
//        binding.refresh.setOnRefreshListener {
//            viewModel.forceLoad()
//        }
//        initViewModel()
//        this.binding = binding
//    }
//
//    private fun initViewModel() {
//        viewModel.observe()
//            .onEach {
//
//            }
//            .produceIn(lifecycleScope)
//        viewModel.movies.observe(
//            viewLifecycleOwner,
//            {
//                adapter.setData(it)
//            }
//        )
//        viewModel.loading.observe(viewLifecycleOwner, LoadingObserver(this))
//        ErrorHandler.handleError(viewModel, this)
//        if (viewModel.movies.value == null) {
//            viewModel.load()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        this.binding = null
//    }
//
    override fun showLoading() {
//        binding?.refresh?.isRefreshing = true
    }

    override fun hideLoading() {
//        binding?.refresh?.isRefreshing = false
    }
}