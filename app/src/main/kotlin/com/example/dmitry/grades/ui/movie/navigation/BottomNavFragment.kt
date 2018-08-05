package com.example.dmitry.grades.ui.movie.navigation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dmitry.grades.R
import com.example.dmitry.grades.ui.base.ToothpickFragment
import com.example.dmitry.grades.ui.movie.favorite.view.FavoriteFragment
import com.example.dmitry.grades.ui.movie.list.view.ListFragment

class BottomNavFragment : ToothpickFragment() {

    private lateinit var bottomNav: BottomNavigationView

    companion object {
        fun newInstance(): BottomNavFragment {
            return BottomNavFragment()
        }

        private const val SELECTED_ID = "com.example.dmitry.grades.ui.movie.navigation.selected_id"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_bootom_nav, container, false)
        bottomNav = rootView.findViewById(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener { it ->
            val fragment: Fragment? = when (it.itemId) {
                R.id.nav_list -> ListFragment.newInstance()
                R.id.nav_favorite -> FavoriteFragment.newInstance()
                else -> null
            }
            fragment?.let {
                setFragment(it)
            }
            fragment != null
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            val selected = bottomNav.selectedItemId
            bottomNav.selectedItemId = selected
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_ID, bottomNav.selectedItemId)
    }

    fun setFragment(it: Fragment): Int {
        return childFragmentManager.beginTransaction()
                .replace(R.id.nav_container, it)
                .commit()
    }
}