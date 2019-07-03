package com.noxel.colorstudio.ui.main.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noxel.colorstudio.*
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.model.SliderModel
import com.noxel.colorstudio.ui.base.BaseFragment
import com.noxel.colorstudio.utils.CONSUMER
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("=========>", "CategoryFragment")
        activity!!.getAppInjector().inject(this)
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSliders()
        getProducts()

    }


    /*sliders part -----------------------------------------------*/
    private val selectedSlide: (SliderModel) -> Unit = {
        if(it.target != null)
            openInstagram(it.target)
        else{

        }
    }


    var sliderAdapter = SliderAdapter(selectedSlide)

    fun getSliders(){
        activity?.withViewModel<HomeViewModel>(viewModelFactory){
            getSliders(true, activity!!)
            observe(slider, ::getSlidersResponse)
        }
    }

    private fun getSlidersResponse(data: Data<List<SliderModel>>?) {
        data?.let {

            when (it.dataState) {

                DataState.LOADING -> {
                    Log.d("=========>", "Loading")

                }
                DataState.SUCCESS -> {
                    Log.d("=========>", "Success")
                    vp_slider.adapter = sliderAdapter
                    vp_slider.rotationY = 180F
                    pageIndicatorView.setViewPager(vp_slider)
                    pageIndicatorView.setAnimationType(AnimationType.SWAP)
                    pageIndicatorView.rotationY = 180F

//                 adapterSliderAdapter.AdapterPagerImage(parentActivity)
                    it.data?.let { it1 -> sliderAdapter.setItems(it1, activity!!) }
                }
                DataState.ERROR -> {
                }
            }
        }
    }

    fun openInstagram(instagramAddress: String){
        val uri = Uri.parse(instagramAddress)
        val likeIng = Intent(Intent.ACTION_VIEW, uri)

        likeIng.setPackage("com.instagram.android")

        try {
            startActivity(likeIng)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(instagramAddress)))
        }

    }
/*products part -----------------------------------------------*/

    private val selectedProduct: (ProductModel) -> Unit = {
    }


    var category : Int? = null
    var featured : Int? = 1
    //    var userType = sharedPreference.retrievedUserType(this)
    var userType = CONSUMER

    fun getProducts(){
        activity?.withViewModel<HomeViewModel>(viewModelFactory){
            getProducts(true, category, featured, activity!!)
            observe(products, ::getProductsResponse)
        }
    }

    private fun getProductsResponse(data: Data<List<ProductModel>>?) {
        data?.let {

            when (it.dataState) {

                DataState.LOADING -> {
                    Log.d("=========>", "Loading")

                }
                DataState.SUCCESS -> {
                    Log.d("=========>", "Success")
                    productsRecycler.adapter = it.data?.let { it1 -> ProductsAdapter(it1, activity!!, selectedProduct, userType) }

                }
                DataState.ERROR -> {
                }
            }
        }
    }
}