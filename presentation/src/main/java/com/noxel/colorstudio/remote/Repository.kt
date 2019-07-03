package com.noxel.colorstudio.remote

import com.noxel.colorstudio.model.*
import io.reactivex.Single

/**
 * Created by asma.
 */


interface GetSlidersRepository {
    fun getSliders(refresh: Boolean) : Single<List<SliderModel>>
}

interface GetProductsRepository {

    fun getProducts(refresh: Boolean, category: Int?, featured: Int?) : Single<List<ProductModel>>
}

interface GetCategoriesRepository {
    fun getCategories(refresh: Boolean) : Single<List<CategoryModel>>
}

interface GetSubCategoriesRepository {
    fun getSubCategories(refresh: Boolean, id: Int) : Single<List<SubCategoryModel>>
}

interface PostSearchRepository {
    fun postSearch(refresh: Boolean, requestBody: SearchRequestModel) : Single<List<SearchModel>>
}