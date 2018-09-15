package com.example.dmitry.grades.ui.base.ui.fragment

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

open class BaseFragment : Fragment() {

    protected val compatActivity: AppCompatActivity?
        get() = activity as? AppCompatActivity
}