package com.noxel.colorstudio.remote

import com.noxel.colorstudio.model.SearchModel
import com.noxel.colorstudio.model.SearchRequestModel
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by asma.
 */


//interface SubmitMobileApi {
//
////    @Headers("content-type: application/json")
////    @POST("customer/submit_mobile/")
////    fun submitMobile( @Body mobile: SubmitMobileBody) : Single<SubmitMobileModel>
//}

interface PostSearchApi {


    @Headers("Content-Type: application/json")
    @POST("/api/product/search/")
    fun postSearch(@Body body:SearchRequestModel): Single<List<SearchModel>>
}
