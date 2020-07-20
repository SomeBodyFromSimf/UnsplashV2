package com.mihailchistousov.unsplashv20.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mihailchistousov.unsplashv20.R
import com.mihailchistousov.unsplashv20.Utils.setTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment(R.layout.fragment_fav) {

    private val favViewModel: FavViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Избранное")
    }
}