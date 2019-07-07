package com.noxel.colorstudio.remote

import com.noxel.colorstudio.model.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
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

interface GetCategoriesApi{
    @GET("api/category")
    fun getCategories(@Query("hair_color") hairColor:Int?) : Single<List<CategoryModel>>
}

interface GetSubCategoriesApi{
    @GET("/api/category/subcategory/{id}")
    fun getSubCategories(@Path("id")id: Int) : Single<List<SubCategoryModel>>
}



