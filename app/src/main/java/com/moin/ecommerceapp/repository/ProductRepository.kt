package com.moin.ecommerceapp.repository

import com.example.ecommerceapp.model.ProductResponse
import com.example.ecommerceapp.network.RetrofitClient

class ProductRepository {
    suspend fun getProductDetails(): ProductResponse {
        return RetrofitClient.api.getProductDetails()
    }
}