package com.mihailchistousov.unsplashv20.utils

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mihailchistousov.unsplashv20.R
import com.mihailchistousov.unsplashv20.ui.Parent

fun Fragment.setTitle(title: String) {
    (activity as? Parent)?.setCustomTitle(title)
}

fun Fragment.changeVisibilityBnvAndToolBar(value: Boolean) {
    (activity as? Parent)?.changeVisibilityBnvAndToolBar(value)
}

fun BottomNavigationView.setupTransactionListener(navController: NavController) {
    setOnNavigationItemSelectedListener {
        if (it.itemId == selectedItemId) return@setOnNavigationItemSelectedListener false
        when (it.itemId) {
            R.id.navigation_fav -> {
                navController.navigate(
                    if (selectedItemId == R.id.navigation_photos)
                        R.id.action_navigation_photos_to_navigation_fav
                    else
                        R.id.action_navigation_info_to_navigation_fav
                )
                true
            }
            R.id.navigation_photos -> {
                navController.navigate(
                    if (selectedItemId == R.id.navigation_info)
                        R.id.action_navigation_info_to_navigation_photos
                    else
                        R.id.action_navigation_fav_to_navigation_photos
                )
                true
            }
            R.id.navigation_info -> {
                navController.navigate(
                    if (selectedItemId == R.id.navigation_fav)
                        R.id.action_navigation_fav_to_navigation_info
                    else
                        R.id.action_navigation_photos_to_navigation_info
                )
                true
            }
            else -> false
        }
    }

}

fun Fragment.showAlertDialog(message: String?, retry: (() -> Unit)? = null) {
    val builder = AlertDialog.Builder(requireContext()).apply {
        setTitle(R.string.error)
        setMessage(message)
        setPositiveButton(R.string.retry) { _, _ ->
            retry?.invoke()
        }
        setNegativeButton(R.string.no) { _, _ ->
        }
    }
    builder.show()
}