package com.noxel.colorstudio.injection.component

import com.noxel.colorstudio.injection.module.AppModule
import com.noxel.colorstudio.injection.module.NetworkModule
import com.noxel.colorstudio.injection.module.RepositoryModule
import com.noxel.colorstudio.injection.module.ViewModelModule
import com.noxel.colorstudio.ui.base.BaseActivity
import com.noxel.colorstudio.ui.base.BaseFragment
import com.noxel.colorstudio.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class), (RepositoryModule::class), (ViewModelModule::class)])
interface Injector {

    fun inject(activity: MainActivity)
    fun inject(activity: BaseActivity)
    fun inject(fragment: BaseFragment)
}
