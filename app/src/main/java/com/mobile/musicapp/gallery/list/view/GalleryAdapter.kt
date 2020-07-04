package com.mobile.musicapp.gallery.list.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.musicapp.R

class GalleryAdapter(val height: Int) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    private var list = mutableListOf<String>()

    var onGalleryImageListener: OnGalleryImageListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = list[position]

        Glide.with(holder.itemView.context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imageView)
    }

    fun addAll(list: List<String>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.galley_item_image)

        init {
            val params = imageView.layoutParams
            params.height = height
            itemView.layoutParams = params
            imageView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onGalleryImageListener?.onGalleryImageClicked(list[adapterPosition])
        }

    }
}