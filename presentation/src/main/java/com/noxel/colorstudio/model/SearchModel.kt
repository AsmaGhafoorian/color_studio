package com.noxel.colorstudio.model

import com.squareup.moshi.Json

data class SearchModel(
        @Json(name = "active") val active: Boolean? = null,
        @Json(name = "barcode") val barcode: String? = null,
        @Json(name = "category") val category: Int? = null,
        @Json(name = "color_code") val color_code: String? = null,
        @Json(name = "description_customer") val description_customer: String? = null,
        @Json(name = "description_salon") val description_salon: String? = null,
        @Json(name = "id") val id: Int? = null,
        @Json(name = "image") var image: String? = null,
        @Json(name = "price") val price: Int? = null,
        @Json(name = "title") val title: String? = null
)