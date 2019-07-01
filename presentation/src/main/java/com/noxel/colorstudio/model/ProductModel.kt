package com.noxel.colorstudio.model

import com.squareup.moshi.Json

data class ProductModel(

        @Json(name = "id") val id : Int ,
        @Json(name = "description_customer") val  description_customer : String? = null ,
        @Json(name = "description_salon") val  description_salon : String? = null ,
        @Json(name = "rate") val rate : Int? = null ,
        @Json(name = "active") val active : Boolean? = null ,
        @Json(name = "title") val title : String? = null ,
        @Json(name = "category") val category : Int? = null ,
        @Json(name = "color_code") val color_code : String? = null ,
        @Json(name = "image") val image : String? = null ,
        @Json(name = "barcode") val barcode : String ? = null

)