package com.mihailchistousov.unsplashv20.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mihailchistousov.unsplashv20.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);

        navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        navView.setOnNavigationItemSelectedListener {
            if(it.itemId == navView.selectedItemId) return@setOnNavigationItemSelectedListener false
            when (it.itemId) {
                R.id.navigation_fav -> {
                    navController.navigate(
                        if (navView.selectedItemId == R.id.navigation_photos)
                            R.id.action_navigation_photos_to_navigation_fav
                        else
                            R.id.action_navigation_info_to_navigation_fav
                    )
                    true
                }
                R.id.navigation_photos -> {
                    navController.navigate(
                        if (navView.selectedItemId == R.id.navigation_info)
                            R.id.action_navigation_info_to_navigation_photos
                        else
                            R.id.action_navigation_fav_to_navigation_photos
                    )
                    true
                }
                R.id.navigation_info -> {
                    navController.navigate(
                        if (navView.selectedItemId == R.id.navigation_fav)
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

    fun setCustomTitle(title: String) {
        toolbar_title.text = title
    }

    fun changeVisibilityBnvAndToolBar(value: Boolean) {
        val constVisibility = if(value) View.VISIBLE else View.GONE
        toolbar.visibility = constVisibility
        navView.visibility = constVisibility
    }

    override fun onBackPressed() {
        if(findNavController(R.id.nav_host_fragment).currentDestination?.id == R.id.navigation_detail_photo)
            changeVisibilityBnvAndToolBar(true)
        super.onBackPressed()
    }
}