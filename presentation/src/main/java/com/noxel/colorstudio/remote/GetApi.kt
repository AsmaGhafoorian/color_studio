package com.noxel.colorstudio.remote

import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.model.SliderModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by asma.
 */

interface GetSlidersApi{
    @GET("api/core/slider/")
    fun getSliders() : Single<List<SliderModel>>
}

interface GetProductsApi{
    @GET("api/product/")
    fun getProducts(@Query("category") category:Int?, @Query("featured") featured : Int?) : Single<List<ProductModel>>
}

