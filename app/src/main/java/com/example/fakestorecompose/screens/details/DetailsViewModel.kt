package com.example.fakestorecompose.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestorecompose.database.ProductEntity
import com.example.fakestorecompose.repository.DetailsRepository
import com.example.fakestorecompose.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<DetailsUiState>>(UiState.Loading)
    val state: StateFlow<UiState<DetailsUiState>> = _uiState.asStateFlow()

    fun onEvent(event: DetailsEvent) {

    }

    fun fetchProduct(id: Int) {
        viewModelScope.launch {
            val product = detailsRepository.getProductByID(id)
            _uiState.value = UiState.Success(
                data = DetailsUiState(
                    product = product,
                )
            )
        }
    }
}