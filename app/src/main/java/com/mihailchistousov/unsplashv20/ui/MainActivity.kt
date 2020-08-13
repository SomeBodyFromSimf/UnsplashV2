package com.mihailchistousov.unsplashv20.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mihailchistousov.unsplashv20.R
import com.mihailchistousov.unsplashv20.utils.setupTransactionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), Parent {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        navView.setupTransactionListener(navController)
    }

    override fun setCustomTitle(title: String) {
        toolbar_title.text = title
    }

    override fun changeVisibilityBnvAndToolBar(value: Boolean) {
        val visibility = if (value) View.VISIBLE else View.GONE
        toolbar.visibility = visibility
        navView.visibility = visibility
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.navigation_detail_photo)
            changeVisibilityBnvAndToolBar(true)
        super.onBackPressed()
    }
}

interface Parent {
    fun changeVisibilityBnvAndToolBar(value: Boolean)
    fun setCustomTitle(title: String)
}