package com.example.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.base.extensions.viewModel
import com.example.base.ui.observers.LoadingObserver
import com.example.base.ui.ui.errors.ErrorHandler
import com.example.base.ui.ui.errors.LoadingView
import com.example.base.ui.ui.fragment.DIFragment
import com.example.details.DetailsViewModel
import com.example.details.R
import com.example.details.di.DetailsModule
import toothpick.config.Module

class DetailsFragment : DIFragment(), LoadingView {

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

    private lateinit var poster: ImageView

    private lateinit var titleTv: TextView

    private lateinit var yearTv: TextView

    private lateinit var timeTv: TextView

    private lateinit var releaseTv: TextView

    private lateinit var aboutTv: TextView

    private lateinit var toolbar: Toolbar

    private lateinit var refresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout

    private lateinit var favoriteBtn: ToggleButton

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
        poster = view.findViewById(R.id.poster)
        titleTv = view.findViewById(R.id.titleTv)
        yearTv = view.findViewById(R.id.yearTv)
        timeTv = view.findViewById(R.id.timeTv)
        releaseTv = view.findViewById(R.id.releaseTv)
        aboutTv = view.findViewById(R.id.aboutTv)
        toolbar = view.findViewById(R.id.toolbar)
        refresh = view.findViewById(R.id.refresh)
        favoriteBtn = view.findViewById(R.id.favoriteBtn)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    titleTv.text = it.title
                    yearTv.text = it.year
                    timeTv.text = it.time
                    releaseTv.text = it.release
                    aboutTv.text = it.about
                    Glide.with(this@DetailsFragment)
                        .load(it.poster).into(poster)
                    favoriteBtn.isChecked = it.isFavorite
                }
            }
        )
        ErrorHandler.handleError(viewModel, this)
        favoriteBtn.setOnClickListener {
            if (favoriteBtn.isChecked) {
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