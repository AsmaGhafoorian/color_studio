package com.noxel.colorstudio.ui.search

import android.app.Activity
import android.arch.lifecycle.ViewModel
import com.noxel.colorstudio.Data
import com.noxel.colorstudio.DataState
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.model.SearchModel
import com.noxel.colorstudio.model.SearchRequestModel
import com.noxel.colorstudio.remote.GetCategoriesRepository
import com.noxel.colorstudio.remote.PostSearchRepository
import io.noxel.presentation.ui.utils.ErrorHandling
import io.noxel.presentation.ui.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchRepository: PostSearchRepository): ViewModel(){

    val search = SingleLiveEvent<Data<MutableList<SearchModel>>>()

    private var compositeDisposable = CompositeDisposable()

    fun postSerches(refresh: Boolean, requestBodey: SearchRequestModel ,activity: Activity){
        compositeDisposable.add(searchRepository.postSearch(refresh, requestBodey)
                .doOnSubscribe{search.postValue(Data(dataState = DataState.LOADING, data = search.value?.data, message = null))}
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    search.postValue(Data(dataState = DataState.SUCCESS, data = it, message = null) as Data<MutableList<SearchModel>>?)
                }, {
                    var error =  ErrorHandling()
                    var detail = error.manageError(it, activity)
                    search.postValue(Data(dataState = DataState.ERROR, data = search.value?.data, message = detail)) }))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}