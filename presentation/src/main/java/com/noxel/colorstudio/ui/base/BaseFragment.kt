package com.noxel.colorstudio.ui.base

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.noxel.colorstudio.getAppInjector
import javax.inject.Inject

open class BaseFragment : Fragment(){


    var activity : BaseActivity? = null


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