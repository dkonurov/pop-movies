package com.example.base.ui.ui.fragment

import toothpick.Scope
import toothpick.Toothpick

data class ToothpickComponent(val scope: Scope) : DiCleanable {

    override fun clean() {
        Toothpick.closeScope(scope.name)
    }
}