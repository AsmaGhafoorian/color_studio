package com.noxel.colorstudio.ui.main

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log
import com.noxel.colorstudio.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.MenuItem
import com.noxel.colorstudio.*
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.model.SliderModel
import com.rd.animation.type.AnimationType
import javax.inject.Inject
import android.content.Intent
import android.content.ActivityNotFoundException
import android.net.Uri
import com.noxel.colorstudio.utils.CONSUMER
import com.noxel.colorstudio.utils.COSTUMER
import com.noxel.colorstudio.utils.SharedPreference


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val homeFragment: Fragment = HomeFragment()
    val profileFragment: Fragment = ProfileFragment()
    val categoryFragment: Fragment = CategoryFragment()
    val educationalFragment: Fragment = EducationalFragment()
    val fm = supportFragmentManager
    var activeFragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAppInjector().inject(this)
        init()
    }

    private fun init(){

        registerToolbar()
        removeShiftMode(bottomNavigation)

        fm.beginTransaction().add(R.id.main_container,homeFragment, "1").commit()
        fm.beginTransaction().add(R.id.main_container, profileFragment, "2").hide(profileFragment).commit()
        fm.beginTransaction().add(R.id.main_container, categoryFragment, "3").hide(categoryFragment).commit()
        fm.beginTransaction().add(R.id.main_container, educationalFragment, "4").hide(educationalFragment).commit()

        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        getSliders()
        getProducts()

}

    var onNavigationItemSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener
        = object: BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when(item.itemId){
                    R.id.navigation_home ->{
                        fm.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                        activeFragment = homeFragment
                        return true
                    }
                    R.id.navigation_profile ->{
                        fm.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                        activeFragment = profileFragment
                        return true
                    }
                    R.id.navigation_category ->{
                        fm.beginTransaction().hide(activeFragment).show(categoryFragment).commit()
                        activeFragment = categoryFragment
                        return true
                    }
                    R.id.navigation_education ->{
                        fm.beginTransaction().hide(activeFragment).show(educationalFragment).commit()
                        activeFragment = educationalFragment
                        return true
                    }
                }
            return false
        }
    }


//    this method remove shift mode on bottom navigation view items
    @SuppressLint("RestrictedApi")
    fun removeShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShifting(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field")
        } catch (e: IllegalAccessException) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode")
        }
    }

/*sliders part -----------------------------------------------*/
    private val selectedSlide: (SliderModel) -> Unit = {
    it.target?.let { it1 -> openInstagram(it1) }

    }
    var sliderAdapter = SliderAdapter(selectedSlide)

    fun getSliders(){
        withViewModel<MainViewModel>(viewModelFactory){
            getSliders(true, this@MainActivity)
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
                    it.data?.let { it1 -> sliderAdapter.setItems(it1, this) }
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
        withViewModel<MainViewModel>(viewModelFactory){
            getProducts(true, category, featured, this@MainActivity)
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
                    productsRecycler.adapter = it.data?.let { it1 -> ProductsAdapter(it1, this, selectedProduct, userType) }

                }
                DataState.ERROR -> {
                }
            }
        }
    }

}
