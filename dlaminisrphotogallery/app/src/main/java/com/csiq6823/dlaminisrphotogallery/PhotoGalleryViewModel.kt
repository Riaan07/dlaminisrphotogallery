package com.csiq6823.dlaminisrphotogallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Search is a case-insensitive filter on authors in the first 100 Picsum photos.
data class PhotoGalleryUiState(
    val images: List<GalleryItem> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val error: Boolean = false
)

class PhotoGalleryViewModel : ViewModel() {
    private val photoRepository = PhotoRepository()
    private val preferencesRepository = PreferencesRepository.get()
    private val _uiState = MutableStateFlow(PhotoGalleryUiState())
    val uiState: StateFlow<PhotoGalleryUiState> = _uiState.asStateFlow()
    private var cachedPhotos: List<GalleryItem> = emptyList()

    init {
        viewModelScope.launch {
            preferencesRepository.storedQuery.collectLatest { query ->
                _uiState.update { it.copy(searchQuery = query, images = filter(query)) }
            }
        }
        refresh()
    }

    private fun filter(query: String): List<GalleryItem> =
        if (query.isBlank()) cachedPhotos
        else cachedPhotos.filter { it.author.contains(query.trim(), ignoreCase = true) }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = false) }
            try {
                cachedPhotos = photoRepository.fetchPhotos(page = 1, limit = 100)
                _uiState.update { it.copy(images = filter(it.searchQuery), isLoading = false) }
            } catch (e: Exception) {
                Log.e("PhotoGallery", "Failed to load photos", e)
                _uiState.update { it.copy(isLoading = false, error = true) }
            }
        }
    }

    fun setQuery(query: String) {
        viewModelScope.launch { preferencesRepository.setStoredQuery(query) }
    }
}
