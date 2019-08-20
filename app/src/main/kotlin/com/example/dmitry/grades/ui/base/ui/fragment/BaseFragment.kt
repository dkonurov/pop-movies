package com.example.dmitry.grades.ui.base.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dmitry.grades.ui.base.ui.errors.ErrorView
import com.google.android.material.snackbar.Snackbar

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