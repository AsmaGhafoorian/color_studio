package com.noxel.colorstudio.ui.magic_mirror

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.noxel.colorstudio.*
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.model.SubCategoryModel
import com.noxel.colorstudio.ui.base.BaseActivity
import com.noxel.colorstudio.ui.main.home.HomeViewModel
import com.noxel.colorstudio.utils.SUB_CAT
import kotlinx.android.synthetic.main.activity_color.*

class ColorActivity: BaseActivity() {

    var colorCatId : Int? = null
    var colorCategory : SubCategoryModel? = null
    var imageUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)

        colorCategory = intent.getSerializableExtra(SUB_CAT) as SubCategoryModel
        colorCatId = colorCategory!!.id
        imageUri = intent.data

        photo.setImageURI(imageUri)
        colorCategory?.image?.let { colorCatImage.loadCircleAvatar(it) }
        colorCatName.text = colorCategory?.title

        getColors()
    }


    /*products part -----------------------------------------------*/

    private val selectedColor: (ProductModel) -> Unit = {
        navigator.navigateToProductDetailsActivity(this, it)
    }

    fun getColors(){
        withViewModel<HomeViewModel>(viewModelFactory){
            getProducts(true, colorCatId, null, this@ColorActivity)
            observe(products, ::getColorsResponse)
        }
    }

    private fun getColorsResponse(data: Data<MutableList<ProductModel>>?) {
        data?.let {

            when (it.dataState) {

                DataState.LOADING -> {
                    Log.d("=========>", "Loading")
                    showPagesLoading()

                }
                DataState.SUCCESS -> {
                    Log.d("=========>", "Success")
                    hidePagesLoading()
                    colorsRecycler.adapter = it.data?.let { it1 -> ColorAdapter(it1, this, selectedColor) }
                }
                DataState.ERROR -> {
                    hidePagesLoading()
                }
            }
        }
    }
}