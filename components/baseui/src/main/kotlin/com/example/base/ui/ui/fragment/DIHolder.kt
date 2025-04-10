package com.example.base.ui.ui.fragment

import androidx.lifecycle.ViewModel

class DIHolder<T>(val component: T) : ViewModel() {


    override fun onCleared() {
        if (component is DiCleanable) {
            component.clean()
        }
        super.onCleared()
    }
}