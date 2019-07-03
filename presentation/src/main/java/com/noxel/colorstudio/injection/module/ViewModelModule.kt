package com.noxel.colorstudio.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.noxel.colorstudio.ViewModelFactory
import com.noxel.colorstudio.ViewModelKey
import com.noxel.colorstudio.ui.main.home.HomeViewModel
import com.noxel.colorstudio.ui.main.category.CategoryViewModel
import com.noxel.colorstudio.ui.main.sub_category.SubCategoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindMainViewModel(factory: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    internal abstract fun bindCategoryViewModel(factory: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubCategoryViewModel::class)
    internal abstract fun bindSubCategoryViewModel(factory: SubCategoryViewModel): ViewModel


}
