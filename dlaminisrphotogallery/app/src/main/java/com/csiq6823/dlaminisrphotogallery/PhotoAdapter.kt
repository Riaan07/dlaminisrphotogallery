package com.csiq6823.dlaminisrphotogallery

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load

class PhotoAdapter : ListAdapter<GalleryItem, PhotoAdapter.PhotoViewHolder>(DIFF) {
    class PhotoViewHolder(private val imageView: ImageView) : RecyclerView.ViewHolder(imageView) {
        fun bind(item: GalleryItem) {
            imageView.contentDescription = imageView.context.getString(R.string.photo_by, item.author)
            // Use Picsum's resized image endpoint so thumbnails do not download full-res files.
            imageView.load("https://picsum.photos/id/${item.id}/300/300") {
                crossfade(true)
                placeholder(android.R.drawable.progress_horizontal)
                error(android.R.drawable.stat_notify_error)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_gallery, parent, false) as ImageView
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<GalleryItem>() {
            override fun areItemsTheSame(a: GalleryItem, b: GalleryItem) = a.id == b.id
            override fun areContentsTheSame(a: GalleryItem, b: GalleryItem) = a == b
        }
    }
}
