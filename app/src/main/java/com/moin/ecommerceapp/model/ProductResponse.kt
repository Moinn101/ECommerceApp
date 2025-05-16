package com.example.ecommerceapp.model

data class ProductResponse(
    val status: Int,
    val message: String,
    val data: ProductData
)

data class ProductData(
    val id: String,
    val sku: String,
    val name: String,
    val price: String,
    val final_price: String,
    val description: String,
    val image: String,
    val images: List<String>,
    val configurable_option: List<ConfigurableOption>,
    val web_url: String,
    val brand_name: String
)

data class ConfigurableOption(
    val attribute_id: Int,
    val type: String,
    val attribute_code: String,
    val attributes: List<ColorAttribute>
)

data class ColorAttribute(
    val value: String,
    val option_id: String,
    val price: String,
    val images: List<String>,
    val swatch_url: String
)