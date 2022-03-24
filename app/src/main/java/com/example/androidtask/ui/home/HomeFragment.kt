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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.adapters.ImagesAdapter
import com.example.androidtask.base.BaseFragment
import com.example.androidtask.data.local.entity.toImageModel
import com.example.androidtask.databinding.HomeFragmentBinding
import com.example.androidtask.utils.Constants
import com.example.androidtask.utils.EndlessScrollListener
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
    private lateinit var scrollListener: EndlessScrollListener
    private var isPagination = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initView()
        initListener()
        collectFlows()
        initObservations()
    }

    private fun initView() {
        if (keyword.isEmpty()){
            keyword = Constants.DEFAULT_KEYWORD_FRUIT
            binding.lblPopular.text = getString(R.string.popular_data, Constants.DEFAULT_KEYWORD_FRUIT)
        } else
            binding.lblPopular.text = getString(R.string.popular_data, keyword)
    }

    private fun initObservations() {
        viewModel.imagesLiveData.observe(viewLifecycleOwner) { images ->
             imagesAdapter.differ.submitList(images)
        }
    }

    private fun initListener() {
        context?.let {

            val gridLayoutManager = GridLayoutManager(binding.recyclerPopularPhotos.context, 2)
            scrollListener = object : EndlessScrollListener(gridLayoutManager, 1) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    isPagination = true
                    viewModel.fetchImagesFromServer(page = page+1, keyword = keyword)
                }

                override fun onCurrentItem(position: Int) {}
            }

            this.imagesAdapter = ImagesAdapter() { image ->
                val bundle = bundleOf(Constants.BUNDLE_KEY to image.toImageModel())
                findNavController().navigate(
                    R.id.toPhotoDetailFragment,
                    bundle
                )
            }
            binding.recyclerPopularPhotos.run {
                layoutManager = gridLayoutManager
                addOnScrollListener(scrollListener)
                binding.recyclerPopularPhotos.adapter = imagesAdapter
            }

            binding.searchInputField.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    isPagination = false
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