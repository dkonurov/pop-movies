package com.example.dmitry.grades.ui.base.ui.activity

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.dmitry.grades.ui.base.ui.errors.ErrorView


abstract class BaseActivity : AppCompatActivity(), ErrorView {

    private var bar: Snackbar? = null

    override fun showMessage(message: String) {
        bar?.dismiss()
        bar = null
        bar = Snackbar.make(window.decorView.rootView, message, Snackbar.LENGTH_SHORT)
        bar?.show()
    }

    override fun onStop() {
        bar?.dismiss()
        super.onStop()
    }

}