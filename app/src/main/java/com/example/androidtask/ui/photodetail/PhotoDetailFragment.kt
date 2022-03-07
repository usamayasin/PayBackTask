package com.example.androidtask.ui.photodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.androidtask.base.BaseFragment
import com.example.androidtask.data.model.Image
import com.example.androidtask.databinding.PhotoDetailFragmentBinding
import com.example.androidtask.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailFragment : BaseFragment<PhotoDetailFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PhotoDetailFragmentBinding
        get() = PhotoDetailFragmentBinding::inflate

    private var image: Image? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.image = requireArguments().getParcelable<Image>(Constants.BUNDLE_KEY)
        this.image?.let {
            setView(image!!)
        }
    }

    private fun setView(image: Image) {
        arguments?.let {
            binding.apply {
                data = image
            }
        }
    }
}