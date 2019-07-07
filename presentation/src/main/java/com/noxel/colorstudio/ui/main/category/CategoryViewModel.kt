package com.noxel.colorstudio.ui.main.category

import android.app.Activity
import android.arch.lifecycle.ViewModel
import com.noxel.colorstudio.Data
import com.noxel.colorstudio.DataState
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.model.SliderModel
import com.noxel.colorstudio.remote.GetCategoriesRepository
import com.noxel.colorstudio.remote.GetSubCategoriesRepository
import io.noxel.presentation.ui.utils.ErrorHandling
import io.noxel.presentation.ui.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val categoriesRepository: GetCategoriesRepository): ViewModel(){

    val categories = SingleLiveEvent<Data<List<CategoryModel>>>()

    private var compositeDisposable = CompositeDisposable()

    fun getCategories(refresh: Boolean, activity: Activity, hairColor : Int?){
        compositeDisposable.add(categoriesRepository.getCategories(refresh, hairColor)
                .doOnSubscribe{categories.postValue(Data(dataState = DataState.LOADING, data = categories.value?.data, message = null))}
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    categories.postValue(Data(dataState = DataState.SUCCESS, data = it, message = null))
                }, {
                    var error =  ErrorHandling()
                    var detail = error.manageError(it, activity)
                    categories.postValue(Data(dataState = DataState.ERROR, data = categories.value?.data, message = detail)) }))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}