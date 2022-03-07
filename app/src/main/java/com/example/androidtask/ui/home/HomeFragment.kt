package com.example.androidtask.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidtask.R
import com.example.androidtask.adapters.ImagesAdapter
import com.example.androidtask.base.BaseFragment
import com.example.androidtask.data.local.entity.toImageModel
import com.example.androidtask.databinding.HomeFragmentBinding
import com.example.androidtask.utils.Constants
import com.example.androidtask.utils.closeSoftKeyboard
import com.example.androidtask.utils.flowWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding
        get() = HomeFragmentBinding::inflate

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var imagesAdapter: ImagesAdapter
    private var keyword: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        collectFlows()
        initObservations()
    }

    private fun initView() {
        binding.lblPopular.text =
            getString(R.string.popular_data, Constants.DEFAULT_KEYWORD_FRUIT)
    }

    private fun initObservations() {
        viewModel.imagesLiveData.observe(viewLifecycleOwner) { images ->
            imagesAdapter.differ.submitList(images)
        }
    }

    private fun initListener() {
        context?.let {
            this.imagesAdapter = ImagesAdapter() { image ->
                val bundle = bundleOf(Constants.BUNDLE_KEY to image.toImageModel())
                findNavController().navigate(
                    R.id.toPhotoDetailFragment,
                    bundle
                )
            }
            binding.recyclerPopularPhotos.adapter = imagesAdapter

            binding.searchInputField.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    v.closeSoftKeyboard()
                    keyword = binding.searchInputField.text.toString()
                    if (binding.searchInputField.text.toString().isEmpty().not()) {
                        binding.lblPopular.text =
                            getString(
                                R.string.popular_data,
                                binding.searchInputField.text.toString()
                            )
                        viewModel.fetchImagesFromServer(keyword = keyword)
                    }
                    return@OnEditorActionListener true
                }
                false
            })
        }
    }

    private fun collectFlows() {
        lifecycleScope.launch {
            launch {
                viewModel.responseMessage.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        showSnackbar(it, binding.rootView)
                    }
            }
        }
    }

}