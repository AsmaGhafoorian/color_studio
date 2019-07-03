package com.noxel.colorstudio.remote

import com.noxel.colorstudio.model.*
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by asma.
 */

//@Singleton
//class GetRequestsRepositoryImp @Inject constructor(
//        private val api : GetRequestsApi,
//        private val cache : Cache<RequestModel>
//        ) : GetRequestsRepository {
//    override val Key = "Requests"
//
//    override fun getRequests(refresh: Boolean, token: String): Single<RequestModel> = when(refresh) {
//        true -> {api.getRequests("token $token").flatMap {set(it)  }}
//        false -> {cache.load(Key).onErrorResumeNext { getRequests(true, token) }}
//    }
//    private fun set(requests: RequestModel) = cache.save(Key, requests)
//}



@Singleton
class GetSlidersRepositoryImp @Inject constructor(
                        private val api : GetSlidersApi
                        ) : GetSlidersRepository {

    override fun getSliders(refresh: Boolean): Single<List<SliderModel>> {
        return api.getSliders().map { it }
    }
}


    @Singleton
    class GetProductsRepositoryImp @Inject constructor(
                           private val api : GetProductsApi
                            ) : GetProductsRepository {

        override fun getProducts(refresh: Boolean, category: Int?, featured: Int?): Single<List<ProductModel>> {
            return api.getProducts(category, featured).map { it }
        }
    }

    @Singleton
    class GetCategoriesRepositoryImp @Inject constructor(
            private val api : GetCategoriesApi
    ) : GetCategoriesRepository {

        override fun getCategories(refresh: Boolean ): Single<List<CategoryModel>> {
            return api.getCategories().map { it }
        }
    }

    @Singleton
    class GetSubCategoriesRepositoryImp @Inject constructor(
            private val api : GetSubCategoriesApi
    ) : GetSubCategoriesRepository {

        override fun getSubCategories(refresh: Boolean, id: Int ): Single<List<SubCategoryModel>> {
            return api.getSubCategories(id).map { it }
        }
    }

    @Singleton
    class PostSearchRepositoryImp @Inject constructor(
            private val api : PostSearchApi
    ) : PostSearchRepository {

        override fun postSearch(refresh: Boolean, requestBody:SearchRequestModel ): Single<List<SearchModel>> {
            return api.postSearch(requestBody).map { it }
        }
    }