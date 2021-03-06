package com.mihailchistousov.unsplashv20.ui.photos

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.mihailchistousov.unsplashv20.R
import com.mihailchistousov.unsplashv20.base.Watcher
import com.mihailchistousov.unsplashv20.model.Photo
import com.mihailchistousov.unsplashv20.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.rec_view.*

@AndroidEntryPoint
class PhotoFragment : Fragment(R.layout.fragment_photo), Watcher {

    private val viewModel: PhotoViewModel by viewModels()

    private lateinit var adapter: PhotosAdapter
    private lateinit var layoutManager: GridLayoutManager

    private val lastVisibleItemPosition: Int
        get() = layoutManager.findLastVisibleItemPosition()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Фотографии")
        initList()
        Glide.get(requireContext()).setMemoryCategory(MemoryCategory.HIGH)
        swipe_container.setOnRefreshListener {
            swipe_container.isRefreshing = false
            viewModel.setStateEvent(MainStateEvent.ClearPage)
        }
        subscribe()
    }

    private fun initList() {
        adapter = PhotosAdapter(this)
        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
        layoutManager = GridLayoutManager(context, spanCount)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = layoutManager
        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (viewModel.photos.value !is DataState.Loading && totalItemCount == lastVisibleItemPosition + 1) {
                    viewModel.setStateEvent(MainStateEvent.AddPage)
                    Log.d("GGGG", "onScrollStateChanged: ")
                }

            }
        })
    }

    private fun subscribe() {
        viewModel.dbChange.observe(viewLifecycleOwner, Observer {
            val (id, status) = it
            if (status == DbENum.ADD) {
                adapter.onAddCompleted(id)
            } else {
                adapter.onRemoveCompleted(id)
            }
        })
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
                is DataState.Error -> {
                    progress_bar.visibility = View.GONE
                    showAlertDialog(it.exception.localizedMessage) {
                        viewModel.setStateEvent(MainStateEvent.GetPhotosEvent)
                    }
                }
                is DataState.Success -> {
                    progress_bar.visibility = View.GONE
                    adapter.setData(it.data)
                }
            }
        })
    }

    override fun showDetailedScreenPhoto(position: Int) {
        changeVisibilityBnvAndToolBar(false)
        val bundle = Bundle().apply {
            putParcelableArrayList(LIST_PHOTOS, adapter.arrayList)
            putInt(POSITION, position)
        }
        findNavController().navigate(
            R.id.action_navigation_photos_to_navigation_detail_photo,
            bundle
        )
    }

    override fun addToDB(photo: Photo) {
        viewModel.setStateEvent(MainStateEvent.AddToDBEvent(photo))
    }

    override fun removeFromDB(photo: Photo) {
        viewModel.setStateEvent(MainStateEvent.RemoveFromDB(photo))
    }

    companion object {
        const val LIST_PHOTOS = "photos"
        const val POSITION = "position"
    }
}