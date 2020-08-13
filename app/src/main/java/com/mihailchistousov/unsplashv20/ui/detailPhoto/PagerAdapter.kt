package com.mihailchistousov.unsplashv20.ui.detailPhoto

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mihailchistousov.unsplashv20.R
import com.mihailchistousov.unsplashv20.base.Watcher
import com.mihailchistousov.unsplashv20.model.PhotoWithLike
import kotlinx.android.synthetic.main.item_page.view.*


class PagerAdapter(
    private val photosList: List<PhotoWithLike>,
    private val watcher: Watcher
) : RecyclerView.Adapter<PagerAdapter.PagerVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))

    override fun getItemCount(): Int = photosList.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        holder.bind(photosList[position])
    }

    fun onRemoveCompleted(id: String) {
        photosList[getIndexById(id)].like = false
        notifyItemChanged(getIndexById(id))
    }

    fun onAddCompleted(id: String) {
        photosList[getIndexById(id)].like = true
        notifyItemChanged(getIndexById(id))
    }

    private fun getIndexById(id: String) = photosList.map { it.photo.id }.lastIndexOf(id)


    inner class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var currentPhoto: PhotoWithLike

        fun bind(photoWL: PhotoWithLike) {
            currentPhoto = photoWL
            with(itemView) {
                progress_bar.visibility = View.VISIBLE
                like_image.setOnClickListener {
                    with(currentPhoto) {
                    if (like)
                        watcher.removeFromDB(photo)
                    else watcher.addToDB(photo)
                } }
                Glide.with(this)
                    .load(currentPhoto.photo.urls.full)
                    .addListener(object: RequestListener<Drawable>{
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            progress_bar.visibility = View.GONE
                            Toast.makeText(context, "Error: ${e?.localizedMessage}", Toast.LENGTH_SHORT).show()
                            return false
                        }
                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            progress_bar.visibility = View.GONE
                            return false
                        }
                    })
                    .fitCenter()
                    .into(photo)
            }
            setLikeImage()

        }

        private fun setLikeImage() {
            itemView.like_image.setImageResource(if (currentPhoto.like) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
        }


    }
}