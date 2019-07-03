package com.noxel.colorstudio.ui.main.sub_category

import android.app.Activity
import android.arch.lifecycle.ViewModel
import com.noxel.colorstudio.Data
import com.noxel.colorstudio.DataState
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.model.SubCategoryModel
import com.noxel.colorstudio.remote.GetCategoriesRepository
import com.noxel.colorstudio.remote.GetSubCategoriesRepository
import io.noxel.presentation.ui.utils.ErrorHandling
import io.noxel.presentation.ui.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SubCategoryViewModel @Inject constructor(private val subCategoriesRepository: GetSubCategoriesRepository): ViewModel(){

    val subCategories = SingleLiveEvent<Data<List<SubCategoryModel>>>()
    val subCategoryProduct = SingleLiveEvent<Data<List<SubCategoryModel>>>()


    private var compositeDisposable = CompositeDisposable()

    fun getSubCategory(refresh: Boolean, categoryId: Int ,activity: Activity){
        compositeDisposable.add(subCategoriesRepository.getSubCategories(refresh, categoryId)
                .doOnSubscribe{subCategories.postValue(Data(dataState = DataState.LOADING, data = subCategories.value?.data, message = null))}
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    subCategories.postValue(Data(dataState = DataState.SUCCESS, data = it, message = null))
                }, {
                    var error =  ErrorHandling()
                    var detail = error.manageError(it, activity)
                    subCategories.postValue(Data(dataState = DataState.ERROR, data = subCategories.value?.data, message = detail)) }))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}