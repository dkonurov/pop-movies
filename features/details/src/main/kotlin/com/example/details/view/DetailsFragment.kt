package com.example.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.base.extensions.viewModel
import com.example.base.ui.observers.LoadingObserver
import com.example.base.ui.ui.errors.ErrorHandler
import com.example.base.ui.ui.errors.LoadingView
import com.example.base.ui.ui.fragment.ToothpickFragment
import com.example.details.DetailsViewModel
import com.example.details.di.DetailsModule
import com.example.dmitry.grades.features.details.R
import com.example.dmitry.grades.features.details.databinding.FragmentDetailBinding
import toothpick.config.Module

class DetailsFragment : ToothpickFragment(), LoadingView {

    companion object {
        private const val MOVIE_ID = "com.example.dmitry.grades.ui.movie.details.movie_id"

        fun newInstance(movieId: Long): DetailsFragment {
            val detailsFragment = DetailsFragment()
            val arguments = Bundle()
            arguments.putLong(MOVIE_ID, movieId)
            detailsFragment.arguments = arguments
            return detailsFragment
        }
    }

    private lateinit var toolbar: Toolbar

    private lateinit var refresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getModules(): Array<Module>? {
        val id = arguments?.getLong(MOVIE_ID)
            ?: throw IllegalArgumentException("Cannot be find Movie id")
        return arrayOf(DetailsModule(id))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        refresh = view.findViewById(R.id.refresh)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        compatActivity?.let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            it.setTitle(R.string.details_title)
        }
        val viewModel = viewModel { getScope().getInstance(DetailsViewModel::class.java) }
        viewModel.loading.observe(viewLifecycleOwner, LoadingObserver(this))
        viewModel.movie.observe(
            viewLifecycleOwner,
            {
                it?.let {

                    binding.titleTv.text = it.title
                    binding.yearTv.text = it.year
                    binding.timeTv.text = it.time
                    binding.releaseTv.text = it.release
                    binding.aboutTv.text = it.about
                    Glide.with(this@DetailsFragment)
                        .load(it.poster).into(binding.poster)
                    binding.favoriteBtn.isChecked = it.isFavorite
                }
            }
        )
        ErrorHandler.handleError(viewModel, this)
        binding.favoriteBtn.setOnClickListener {
            if (binding.favoriteBtn.isChecked) {
                viewModel.saveFavorite()
            } else {
                viewModel.removeFavorite()
            }
        }
        if (viewModel.movie.value == null) {
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