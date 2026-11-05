package com.csiq6823.dlaminisrphotogallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.csiq6823.dlaminisrphotogallery.databinding.FragmentPhotoGalleryBinding
import kotlinx.coroutines.launch

class PhotoGalleryFragment : Fragment() {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding get() = checkNotNull(_binding) { "View binding unavailable" }
    private val galleryViewModel: PhotoGalleryViewModel by viewModels()
    private val photoAdapter = PhotoAdapter()
    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoGalleryBinding.inflate(inflater, container, false)
        binding.photoGrid.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.photoGrid.adapter = photoAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.retryButton.setOnClickListener { galleryViewModel.refresh() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                galleryViewModel.uiState.collect { state ->
                    photoAdapter.submitList(state.images)
                    binding.loadingIndicator.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    val message = when {
                        state.error -> R.string.load_error
                        !state.isLoading && state.images.isEmpty() -> R.string.no_results
                        else -> null
                    }
                    binding.messageContainer.visibility = if (message != null) View.VISIBLE else View.GONE
                    message?.let { binding.statusMessage.setText(it) }
                    binding.retryButton.visibility = if (state.error) View.VISIBLE else View.GONE
                }
            }
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_photo_gallery, menu)
                val item = menu.findItem(R.id.menu_item_search)
                val search = item.actionView as? SearchView ?: return
                searchView = search
                search.queryHint = getString(R.string.search_hint)
                search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        galleryViewModel.setQuery(query.orEmpty())
                        search.clearFocus()
                        return true
                    }
                    override fun onQueryTextChange(newText: String?): Boolean = false
                })
                // Prepopulate the field when reopening search after an app restart.
                val saved = galleryViewModel.uiState.value.searchQuery
                if (saved.isNotEmpty()) search.setQuery(saved, false)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
                R.id.menu_item_clear -> {
                    galleryViewModel.setQuery("")
                    searchView?.setQuery("", false)
                    true
                }
                else -> false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        searchView = null
        binding.photoGrid.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
