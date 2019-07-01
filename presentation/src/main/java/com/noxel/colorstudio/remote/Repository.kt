package com.noxel.colorstudio.remote

import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.model.SliderModel
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
