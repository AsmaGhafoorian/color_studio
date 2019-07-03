package com.noxel.colorstudio.ui.main.sub_category

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.noxel.colorstudio.*
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.model.SubCategoryModel
import com.noxel.colorstudio.ui.base.BaseActivity
import com.noxel.colorstudio.ui.main.category.CategoryAdapter
import com.noxel.colorstudio.ui.main.category.CategoryViewModel
import com.noxel.colorstudio.ui.main.home.HomeViewModel
import com.noxel.colorstudio.ui.main.home.ProductsAdapter
import com.noxel.colorstudio.utils.CONSUMER
import com.noxel.colorstudio.utils.SUB_CAT_ID
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.displayMetrics

class SubCategoryActivity: BaseActivity() {

    private var mScreenHeight: Int = 0
    private var mScreenWidth: Int = 0
    private var categoryId: Int = 0

    private lateinit var headLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)


        registerToolbar()

        headLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecycler.setLayoutManager(headLayoutManager)
        horizontalRecycler.setHasFixedSize(true)

        categoryId = intent.getIntExtra(SUB_CAT_ID,0)

        getSubCategories()
        getProducts()

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        mScreenHeight = displayMetrics.heightPixels
        mScreenWidth = displayMetrics.widthPixels



//        back.setOnClickListener {
//            onBackPressed()
//        }

    }


    /* get Categories list -----------------------------------------------*/

    private val selectedCategory: (SubCategoryModel) -> Unit = {

        it.id?.let {
            it -> categoryId = it
            getProducts()
        }

    }


    fun getSubCategories(){
       withViewModel<SubCategoryViewModel>(viewModelFactory){
           getSubCategory(true, categoryId, this@SubCategoryActivity)
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
                    horizontalRecycler.adapter = it.data?.let { it1 -> SubCategoryHorizontalAdapter(it1, this, selectedCategory) }
                }
                DataState.ERROR -> {
                }
            }
        }
    }


    /*products part -----------------------------------------------*/

    private val selectedProduct: (ProductModel) -> Unit = {
        navigator.navigateToProductDetailsActivity(this, it)
    }

    //    var userType = sharedPreference.retrievedUserType(this)
    var userType = CONSUMER

    fun getProducts(){
        withViewModel<HomeViewModel>(viewModelFactory){
            getProducts(true, categoryId, null, this@SubCategoryActivity)
            observe(products, ::getProductsResponse)
        }
    }

    private fun getProductsResponse(data: Data<MutableList<ProductModel>>?) {
        data?.let {

            when (it.dataState) {

                DataState.LOADING -> {
                    Log.d("=========>", "Loading")

                }
                DataState.SUCCESS -> {
                    Log.d("=========>", "Success")
                    verticalRecycler.setItemAnimator(DefaultItemAnimator())
                    verticalRecycler.adapter = it.data?.let { it1 -> SubCategoryVerticalAdapter(it1, this, selectedProduct, userType, mScreenWidth/2) }
                    verticalRecycler.setLayoutManager(GridLayoutManager(this, 2))

                }
                DataState.ERROR -> {
                }
            }
        }
    }
}