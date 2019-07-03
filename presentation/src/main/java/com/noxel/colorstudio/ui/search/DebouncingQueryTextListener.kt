package com.noxel.colorstudio.ui.search

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.widget.SearchView
import kotlinx.coroutines.*

internal class DebouncingQueryTextListener(
    lifecycle: Lifecycle,
    private val onDebouncingQueryTextChange: (String?) -> Unit
) : SearchView.OnQueryTextListener, LifecycleObserver {
    var debouncePeriod: Long = 500

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private var searchJob: Job? = null

    init {
        lifecycle.addObserver(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
//        searchJob = coroutineScope.launch {
//            newText?.let {
//                delay(debouncePeriod)
//                onDebouncingQueryTextChange(newText)
//            }
//        }
        return false
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    private fun destroy() {
//        searchJob?.cancel()
//    }
}