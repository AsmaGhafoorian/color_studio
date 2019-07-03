package com.noxel.colorstudio.injection.module

import com.noxel.colorstudio.remote.*
import dagger.Binds
import dagger.Module

   @Module
   abstract class RepositoryModule {

      @Binds
      abstract fun GetSlidersRepository(repository: GetSlidersRepositoryImp): GetSlidersRepository

      @Binds
      abstract fun GetProductsRepository(repository: GetProductsRepositoryImp): GetProductsRepository

      @Binds
      abstract fun GetCategoriesRepository(repository: GetCategoriesRepositoryImp): GetCategoriesRepository

      @Binds
      abstract fun GetSubCategoriesRepository(repository: GetSubCategoriesRepositoryImp): GetSubCategoriesRepository

      @Binds
      abstract fun PostSearchRepository(repository: PostSearchRepositoryImp): PostSearchRepository

   }
