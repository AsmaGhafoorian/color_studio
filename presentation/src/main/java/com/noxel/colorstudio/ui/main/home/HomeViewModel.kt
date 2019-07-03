package com.noxel.colorstudio.ui.main.home

import android.app.Activity
import android.arch.lifecycle.ViewModel
import com.noxel.colorstudio.Data
import com.noxel.colorstudio.DataState
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.model.SliderModel
import com.noxel.colorstudio.remote.GetProductsRepository
import com.noxel.colorstudio.remote.GetSlidersRepository
import io.noxel.presentation.ui.utils.ErrorHandling
import io.noxel.presentation.ui.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class HomeViewModel @Inject constructor(private val slidersRepository: GetSlidersRepository,
                                        private val productsRepository: GetProductsRepository): ViewModel(){

    val slider = SingleLiveEvent<Data<List<SliderModel>>>()

    val products = SingleLiveEvent<Data<MutableList<ProductModel>>>()

    private var compositeDisposable = CompositeDisposable()

    fun getSliders(refresh: Boolean, activity: Activity){
        compositeDisposable.add(slidersRepository.getSliders(refresh)
                .doOnSubscribe{slider.postValue(Data(dataState = DataState.LOADING, data = slider.value?.data, message = null))}
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    slider.postValue(Data(dataState = DataState.SUCCESS, data = it, message = null))
                }, {
                    var error =  ErrorHandling()
                    var detail = error.manageError(it, activity)
                    slider.postValue(Data(dataState = DataState.ERROR, data = slider.value?.data, message = detail)) }))
    }


    fun getProducts(refresh: Boolean, category: Int?, featured: Int? ,activity: Activity){
        compositeDisposable.add(productsRepository.getProducts(refresh, category, featured)
                .doOnSubscribe{products.postValue(Data(dataState = DataState.LOADING, data = products.value?.data, message = null))}
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    products.postValue(Data(dataState = DataState.SUCCESS, data = it, message = null) as Data<MutableList<ProductModel>>?)
                }, {
                    var error =  ErrorHandling()
                    var detail = error.manageError(it, activity)
                    products.postValue(Data(dataState = DataState.ERROR, data = products.value?.data, message = detail)) }))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}