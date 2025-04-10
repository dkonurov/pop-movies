package com.example.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.base.ui.ui.fragment.ToothpickFragment
import com.example.dmitry.grades.features.navigations.R
import com.example.dmitry.grades.features.navigations.databinding.FragmentBootomNavBinding
import com.example.favorite.list.view.FavoriteFragment
import com.example.movie.list.view.MovieListFragment
import com.example.navigation.di.BottomNavigationModule
import com.google.android.material.bottomnavigation.BottomNavigationView
import toothpick.config.Module

class BottomNavFragment : ToothpickFragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun newInstance(): BottomNavFragment {
            return BottomNavFragment()
        }

        private const val SELECTED_ID = "com.example.navigation.selected_id"
    }

    private var needLoad = false

    private var binding: FragmentBootomNavBinding? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment? = when (item.itemId) {
            R.id.nav_list -> MovieListFragment.newInstance()
            R.id.nav_favorite -> FavoriteFragment.newInstance()
            else -> null
        }
        fragment?.let(this::setFragment)
        return fragment != null
    }

    override fun getModules(): Array<Module>? {
        return arrayOf(BottomNavigationModule())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            needLoad = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_bootom_nav, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBootomNavBinding.bind(view)
        binding.bottomNav.setOnNavigationItemSelectedListener(this)
        if (needLoad) {
            setFragment(MovieListFragment.newInstance())
            needLoad = false
        }
        this.binding = binding
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_ID, requireNotNull(binding).bottomNav.selectedItemId)
    }

    private fun setFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.nav_container, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}