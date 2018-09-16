package com.example.dmitry.grades.ui.base.ui.fragment

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.dmitry.grades.ui.base.ui.errors.ErrorView

open class BaseFragment : Fragment(), ErrorView {

    private var snackbar: Snackbar? = null

    override fun showMessage(message: String) {
        view?.let {
            snackbar?.dismiss()
            snackbar = Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
            snackbar?.show()
        }
    }

    override fun onDestroyView() {
        snackbar?.dismiss()
        snackbar = null
        super.onDestroyView()
    }

    protected val compatActivity: AppCompatActivity?
        get() = activity as? AppCompatActivity
}