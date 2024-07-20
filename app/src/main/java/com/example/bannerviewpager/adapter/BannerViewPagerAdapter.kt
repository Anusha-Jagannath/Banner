package com.example.bannerviewpager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bannerviewpager.R
import com.example.bannerviewpager.databinding.ItemImageBinding
import com.example.bannerviewpager.model.ImageItem

class BannerViewPagerAdapter(private var imagesList: ArrayList<ImageItem>) :
    RecyclerView.Adapter<BannerViewHolder>() {


    private lateinit var binding: ItemImageBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        binding = ItemImageBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        )
        return BannerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val currentItem = imagesList[position]
        holder.bind(currentItem)
    }
}


class BannerViewHolder(private val binding: ItemImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(imageItem: ImageItem) {
        binding.imageView.setImageResource(imageItem.id)
    }
}