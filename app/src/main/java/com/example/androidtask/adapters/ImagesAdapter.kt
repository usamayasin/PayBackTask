package com.example.androidtask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.data.local.entity.ImagesEntity
import com.example.androidtask.databinding.PhotoItemLayoutBinding

class ImagesAdapter (val onImageClicked: (image: ImagesEntity) -> Unit) :
    RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val binding = PhotoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.bind(differ.currentList[position], position)
    }

    override fun getItemCount() = differ.currentList.size

    inner class ImagesViewHolder(private val itemBinding: PhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(image: ImagesEntity, position: Int) {
            itemBinding.apply {
                data = image
                cardPhoto.setOnClickListener {
                    onImageClicked(image)
                }
            }
        }
    }

    private val differCallBack  = object : DiffUtil.ItemCallback<ImagesEntity, >() {

        override fun areItemsTheSame(oldItem: ImagesEntity, newItem: ImagesEntity, ): Boolean {
            return  oldItem.id== newItem.id
        }
        override fun areContentsTheSame(oldItem: ImagesEntity, newItem: ImagesEntity, ): Boolean {
            return  oldItem==newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
}
