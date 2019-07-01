package com.noxel.colorstudio.utils

import android.content.Context
import javax.inject.Inject

/**
 * Created by asma.
 */
class SharedPreference @Inject constructor(){


    val TOKEN_ID = "token_id"
    val TOKEN = "token"

     fun storeToken(context: Context, token: String) {

        var preference = context.getSharedPreferences(TOKEN_ID, 0)
        val editor = preference.edit()
        editor.putString(TOKEN, token)
        editor.commit()
    }

     fun retrievedToken(context: Context): String {

        val prefs = context.getSharedPreferences(TOKEN_ID, 0)
        var token =  prefs.getString(TOKEN, "")
        return token
    }



    val USER_TYPE_ID = "user_type_id"
    val USER_TYPE = "user_type"
    fun storeUserType(context: Context, userType: Int) {

        var preference = context.getSharedPreferences(USER_TYPE_ID, 0)
        val editor = preference.edit()
        editor.putInt(USER_TYPE, userType)
        editor.commit()
    }

    fun retrievedUserType(context: Context): String {

        val prefs = context.getSharedPreferences(USER_TYPE_ID, 0)
        var userType =  prefs.getString(USER_TYPE, "")
        return userType
    }
}