package com.mihailchistousov.unsplashv20.Utils

import androidx.fragment.app.Fragment
import com.mihailchistousov.unsplashv20.ui.MainActivity

 fun Fragment.setTitle(title: String) {
    (activity as MainActivity).setCustomTitle(title)
}

fun Fragment.changeVisibilityBnvAndToolBar(value: Boolean) {
    (activity as MainActivity).changeVisibilityBnvAndToolBar(value)
}