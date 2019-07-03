package com.noxel.colorstudio.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.noxel.colorstudio.R
import com.noxel.colorstudio.getAppInjector
import com.noxel.colorstudio.navigation.Navigator
import com.noxel.colorstudio.utils.SharedPreference
import javax.inject.Inject


/**
 * Created by asma.
 */
open class BaseActivity :AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var sharedPreference: SharedPreference
    @Inject
    lateinit var navigator: Navigator

    var customToolbar: View? = null


    var token = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppInjector().inject(this)
    }


    fun registerToolbar(){
        val layout = ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val actionBar = supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(false)

        val inflater = LayoutInflater.from(this)
        customToolbar = inflater.inflate(R.layout.toolbar, null)

        actionBar.setCustomView(customToolbar, layout)
        actionBar.setDisplayShowCustomEnabled(true)
        val parent = actionBar.customView.parent as Toolbar
        parent.setContentInsetsAbsolute(0, 0)
    }

}