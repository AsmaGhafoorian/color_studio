package com.noxel.colorstudio.model

import com.squareup.moshi.Json

data class SubCategoryModel (

        @Json(name="id") var id: Int? = null,
        @Json(name="image") var image: String? = null,
        @Json(name="title") var title: String? = null

)