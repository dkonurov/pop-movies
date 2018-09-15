package com.example.dmitry.grades.ui.movie.details.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.bumptech.glide.Glide
import com.example.dmitry.grades.R
import com.example.dmitry.grades.di.MovieId
import com.example.dmitry.grades.di.Scopes
import com.example.dmitry.grades.domain.models.PrimitiveWrapper
import com.example.dmitry.grades.ui.base.ui.fragment.DIFragment
import com.example.dmitry.grades.ui.movie.details.DetailsViewModel
import com.example.dmitry.grades.ui.movie.details.DetailsViewModelFactory
import toothpick.Toothpick
import toothpick.config.Module

class DetailsFragment : DIFragment() {

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

    private lateinit var factory: DetailsViewModelFactory

    private lateinit var poster: ImageView

    private lateinit var titleTv: TextView

    private lateinit var yearTv: TextView

    private lateinit var timeTv: TextView

    private lateinit var releaseTv: TextView

    private lateinit var aboutTv: TextView

    private lateinit var toolbar: Toolbar

    private lateinit var refresh: SwipeRefreshLayout

    private lateinit var favoriteBtn: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factory = Toothpick.openScope(Scopes.MOVIE_DETAILS_SCOPE).getInstance(DetailsViewModelFactory::class.java)
        setHasOptionsMenu(true)
    }

    override fun createScope(savedInstanceState: Bundle?) {
        arguments?.let {
            val scope = Toothpick.openScopes(Scopes.REMOTE_SCOPE, Scopes.MOVIE_DETAILS_SCOPE)
            scope.installModules(object : Module() {
                init {
                    bind(PrimitiveWrapper::class.java)
                            .withName(MovieId::class.java)
                            .toInstance(PrimitiveWrapper(it.getLong(MOVIE_ID)))
                }
            })
        }
    }

    override fun closeScope() {
        Toothpick.closeScope(Scopes.MOVIE_DETAILS_SCOPE)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        val viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        viewModel.loading.observe(this, Observer {
            it?.let {
                refresh.isRefreshing = it
            }
        })
        viewModel.movie.observe(this, Observer {
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
        })
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
}