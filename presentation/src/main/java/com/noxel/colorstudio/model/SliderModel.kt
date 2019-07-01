package com.noxel.colorstudio.model

import com.squareup.moshi.Json

data class SliderModel(
        @Json(name = "id") val id: String?,
        @Json(name = "image") val image: String? = null,
        @Json(name = "target") val target: String? = null,
        @Json(name = "product") val product: Int? = null
)