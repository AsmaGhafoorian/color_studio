package com.noxel.colorstudio.model

data class SearchRequestModel(
        var title: String? = null,
        var barcode: String? = null,
        var color_code: String? = null
)