package com.noxel.colorstudio.ui.base

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.noxel.colorstudio.ViewModelFactory
import com.noxel.colorstudio.getAppInjector
import com.noxel.colorstudio.navigation.Navigator
import javax.inject.Inject

open class BaseFragment : Fragment(){


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var activity : BaseActivity? = null
    @Inject
    lateinit var navigator: Navigator


    override fun onAttach(context: Activity?) {

        if(context is BaseActivity){
            activity = context as BaseActivity
        }
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.getAppInjector().inject(this)
    }

}