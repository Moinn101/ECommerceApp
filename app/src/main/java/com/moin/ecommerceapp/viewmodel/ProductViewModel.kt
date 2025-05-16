package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.ProductData
import com.moin.ecommerceapp.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _productData = MutableLiveData<ProductData>()
    val productData: LiveData<ProductData> get() = _productData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchProductDetails() {
        viewModelScope.launch {
            try {
                val response = repository.getProductDetails()
                if (response.status == 200) {
                    _productData.postValue(response.data)
                } else {
                    _error.postValue("Failed to load product: ${response.message}")
                }
            } catch (e: Exception) {
                _error.postValue("Network error: ${e.message}")
            }
        }
    }
}