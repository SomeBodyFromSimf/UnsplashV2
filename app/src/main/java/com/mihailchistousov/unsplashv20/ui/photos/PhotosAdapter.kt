package com.mihailchistousov.unsplashv20.ui.photos

import com.mihailchistousov.unsplashv20.base.BaseAdapter
import com.mihailchistousov.unsplashv20.base.Watcher


class PhotosAdapter(
    watcher: Watcher
) : BaseAdapter(watcher) {

    override fun onRemoveCompleted(id: String) {
        dataList[getIndexById(id)].like = false
        notifyItemChanged(getIndexById(id))
    }

    fun onAddCompleted(id: String) {
        dataList[getIndexById(id)].like = true
        notifyItemChanged(getIndexById(id))
    }

}