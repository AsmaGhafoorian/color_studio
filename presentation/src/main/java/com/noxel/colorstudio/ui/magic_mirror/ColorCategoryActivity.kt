package com.noxel.colorstudio.ui.magic_mirror

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.noxel.colorstudio.*
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.model.SubCategoryModel
import com.noxel.colorstudio.ui.base.BaseActivity
import com.noxel.colorstudio.ui.main.category.CategoryViewModel
import com.noxel.colorstudio.ui.main.sub_category.SubCategoryViewModel
import kotlinx.android.synthetic.main.activity_color_category.*

class ColorCategoryActivity: BaseActivity() {


    var imageUri : Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_category)

        imageUri = intent.data

        photo.setImageURI(imageUri)
        getCategories()
    }


    /* get Categories list -----------------------------------------------*/


    var categoryId : Int? = null
    var hairColor = 1
    fun getCategories(){
       withViewModel<CategoryViewModel>(viewModelFactory){
            getCategories(true, this@ColorCategoryActivity, hairColor)
            observe(categories, ::getCategoriesResponse)
        }
    }

    private fun getCategoriesResponse(data: Data<List<CategoryModel>>?) {
        data?.let {

            when (it.dataState) {

                DataState.LOADING -> {
                    Log.d("=========>", "Loading")
                    showPagesLoading()

                }
                DataState.SUCCESS -> {
                    Log.d("=========>", "Success")
                    hidePagesLoading()
                    categoryId = it.data?.get(0)?.id
                    getSubCategories()
                }
                DataState.ERROR -> {
                }
            }
        }
    }

    /* get Categories list -----------------------------------------------*/


    private val selectedCategory: (SubCategoryModel) -> Unit = {

        imageUri?.let { it1 -> navigator.navigateToColorActivity(this, it, it1) }

    }


    fun getSubCategories(){
        withViewModel<SubCategoryViewModel>(viewModelFactory){
            categoryId?.let { getSubCategory(true, it, this@ColorCategoryActivity) }
            observe(subCategories, ::getSubCategoryResponse)
        }
    }

    private fun getSubCategoryResponse(data: Data<List<SubCategoryModel>>?) {
        data?.let {

            when (it.dataState) {

                DataState.LOADING -> {
                    Log.d("=========>", "Loading")


                }
                DataState.SUCCESS -> {
                    Log.d("=========>", "Success")
                    colorsRecycler.adapter = it.data?.let { it1 -> ColorCategoryAdapter(it1, this, selectedCategory) }
                }
                DataState.ERROR -> {
                }
            }
        }
    }

}