package com.mihailchistousov.unsplashv20.ui.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mihailchistousov.unsplashv20.R
import com.mihailchistousov.unsplashv20.utils.setTitle

class InfoFragment : Fragment(R.layout.fragment_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Информация")
    }
}