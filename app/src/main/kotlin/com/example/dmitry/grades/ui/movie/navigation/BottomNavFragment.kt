package com.example.dmitry.grades.ui.movie.navigation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.dmitry.grades.R
import com.example.dmitry.grades.ui.base.ui.fragment.DIFragment
import com.example.dmitry.grades.ui.movie.favorite.view.FavoriteFragment
import com.example.dmitry.grades.ui.movie.list.view.ListFragment

class BottomNavFragment : DIFragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var needLoad = false

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: androidx.fragment.app.Fragment? = when (item.itemId) {
            R.id.nav_list -> ListFragment.newInstance()
            R.id.nav_favorite -> FavoriteFragment.newInstance()
            else -> null
        }
        fragment?.let {
            setFragment(it)
        }
        return fragment != null
    }

    private lateinit var bottomNav: BottomNavigationView

    companion object {
        fun newInstance(): BottomNavFragment {
            return BottomNavFragment()
        }

        private const val SELECTED_ID = "com.example.dmitry.grades.ui.movie.navigation.selected_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            needLoad = true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_bootom_nav, container, false)
        bottomNav = rootView.findViewById(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener(this)
        if (needLoad) {
            setFragment(ListFragment.newInstance())
            needLoad = false
        }
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_ID, bottomNav.selectedItemId)
    }

    fun setFragment(it: androidx.fragment.app.Fragment): Int {
        return childFragmentManager.beginTransaction()
                .replace(R.id.nav_container, it)
                .commit()
    }
}