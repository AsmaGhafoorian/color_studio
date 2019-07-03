package com.noxel.colorstudio

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.noxel.colorstudio.injection.component.DaggerInjector
import com.noxel.colorstudio.injection.component.Injector
import com.noxel.colorstudio.injection.module.AppModule
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class App : Application() {

    lateinit var injector: Injector private set

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initTimber()
        initRxPaper()
        initRxJavaPluginsErrorHandler()

    }

    private fun initDagger() {
        injector = DaggerInjector
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initRxPaper() = RxPaperBook.init(this)

    private fun initRxJavaPluginsErrorHandler() = RxJavaPlugins.setErrorHandler { Timber.e(it) }
}
