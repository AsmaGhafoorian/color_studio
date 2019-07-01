package io.noxel.presentation.ui.utils

import android.app.Activity
import com.noxel.colorstudio.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by asma.
 */
open class ErrorHandling {
    fun manageError(throwable: Throwable, activity: Activity) : String?{
        var detail : String? = ""
        if((throwable is HttpException)) {
            when((throwable as HttpException).code()){
                in 500..599->  detail = activity.getString(R.string.server_error)

                in 400..499 ->   detail = activity.getString(R.string.server_disconnect)

            }
//            if ((throwable as HttpException).code() == 500) {
//                detail = activity.getString(R.string.server_disconnect)
//            } else {
//                val body = (throwable as HttpException).response().errorBody()
//                val gson = Gson()
////                val adapter = gson.getAdapter(ErrorItem::class.java)
////
////                detail = try {
////                        val errorParser = adapter.fromJson(body!!.string()) as ErrorItem
////                        errorParser.detail!!
////
////                } catch (e: IOException) {
////                    activity.getString(R.string.server_error)
////
////                }
//            }
        }else{
            if(throwable is IOException){
                if(throwable is SocketTimeoutException){
                    detail = activity.getString(R.string.time_out)
                }else
                    detail = activity.getString(R.string.no_connection)
            }else{
                if(throwable is NoSuchElementException){
                    detail = activity.getString(R.string.server_error)
                }
            }
        }
        return detail
    }
}