package com.noxel.colorstudio.injection.module

import com.noxel.colorstudio.remote.GetProductsRepository
import com.noxel.colorstudio.remote.GetProductsRepositoryImp
import com.noxel.colorstudio.remote.GetSlidersRepository
import com.noxel.colorstudio.remote.GetSlidersRepositoryImp
import dagger.Binds
import dagger.Module

   @Module
   abstract class RepositoryModule {

      @Binds
      abstract fun GetSlidersRepository(repository: GetSlidersRepositoryImp): GetSlidersRepository

      @Binds
      abstract fun GetProductsRepository(repository: GetProductsRepositoryImp): GetProductsRepository

   }
