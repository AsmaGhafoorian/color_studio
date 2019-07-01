package com.noxel.colorstudio.injection.module

import com.noxel.colorstudio.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Provides
    @Singleton
    fun provideApp(): App = app
}
