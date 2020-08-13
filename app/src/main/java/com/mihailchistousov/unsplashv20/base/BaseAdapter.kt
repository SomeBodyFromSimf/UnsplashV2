package com.mihailchistousov.unsplashv20.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mihailchistousov.unsplashv20.R
import com.mihailchistousov.unsplashv20.model.PhotoWithLike
import kotlinx.android.synthetic.main.layout_recycler_item.view.*

abstract class BaseAdapter(
    val watcher: Watcher
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    protected var dataList: MutableList<PhotoWithLike> = mutableListOf()
    val arrayList: ArrayList<PhotoWithLike>
        get() = arrayListOf<PhotoWithLike>().apply {
            dataList.forEach {
                add(it)
            }
        }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_recycler_item, parent, false)
        return BaseViewHolder(view)
    }

    fun getIndexById(id: String) = dataList.map { it.photo.id }.lastIndexOf(id)

    fun setData(photos: List<PhotoWithLike>) {
        dataList = photos.toMutableList()
        notifyDataSetChanged()
    }

    abstract fun onRemoveCompleted(id: String)

    inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var photoWL: PhotoWithLike

        fun bind(photo: PhotoWithLike) {
            photoWL = photo
            with(itemView) {
                Glide.with(context)
                    .load(photoWL.photo.urls.thumb)
                    .into(photo_image)
            }
            setLikeImage()
            setListeners()
        }

        private fun setLikeImage() {
            itemView.like_image.setImageResource(if (photoWL.like) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
        }

        private fun setListeners() {
            with(itemView) {
                like_image.setOnClickListener {
                    with(photoWL) {
                        if (like)
                            watcher.removeFromDB(photo)
                        else watcher.addToDB(photo)
                    }
                }
                photo_image.setOnClickListener {
                    watcher.showDetailedScreenPhoto(adapterPosition)
                }
            }
        }
    }
}





