package com.mihailchistousov.unsplashv20.ui.detailPhoto

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mihailchistousov.unsplashv20.R
import com.mihailchistousov.unsplashv20.base.Watcher
import com.mihailchistousov.unsplashv20.model.Photo
import com.mihailchistousov.unsplashv20.model.PhotoWithLike
import com.mihailchistousov.unsplashv20.ui.photos.PhotoFragment
import com.mihailchistousov.unsplashv20.utils.DbENum
import com.mihailchistousov.unsplashv20.utils.MainStateEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail_photo.*

@AndroidEntryPoint
class DetailPhotoFragment : Fragment(R.layout.fragment_detail_photo), Watcher {

    private val viewModel: DetailPhotoViewModel by viewModels()

    private lateinit var adapter: PagerAdapter
    private lateinit var photoList: List<PhotoWithLike>
    private var position: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoList = requireArguments().getParcelableArrayList<PhotoWithLike>(PhotoFragment.LIST_PHOTOS)?.toList()!!
        position = requireArguments().getInt(PhotoFragment.POSITION)
        initList()
        subscribe()
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
    }

    private fun initList() {
        adapter = PagerAdapter(photoList, this)
        viewPager.adapter = adapter
        viewPager.currentItem = position
    }

    override fun addToDB(photo: Photo) {
        viewModel.setStateEvent(MainStateEvent.AddToDBEvent(photo))
    }

    override fun removeFromDB(photo: Photo) {
        viewModel.setStateEvent(MainStateEvent.RemoveFromDB(photo))
    }

}