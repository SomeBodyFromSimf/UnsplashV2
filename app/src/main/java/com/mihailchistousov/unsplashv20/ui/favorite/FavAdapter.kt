package com.mihailchistousov.unsplashv20.ui.favorite

import com.mihailchistousov.unsplashv20.base.BaseAdapter
import com.mihailchistousov.unsplashv20.base.Watcher


class FavAdapter(
    watcher: Watcher
) : BaseAdapter(watcher) {

    override fun onRemoveCompleted(id: String) {
        dataList.removeAt(getIndexById(id))
        notifyItemRemoved(getIndexById(id))
    }
}