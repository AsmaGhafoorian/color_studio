package com.noxel.colorstudio.ui.main

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log
import com.noxel.colorstudio.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.noxel.colorstudio.*
import com.noxel.colorstudio.ui.main.category.CategoryFragment
import com.noxel.colorstudio.ui.main.home.HomeFragment
import javax.inject.Inject


class MainActivity : BaseActivity() {


    val homeFragment: Fragment = HomeFragment()
    val profileFragment: Fragment = ProfileFragment()
    val categoryFragment: Fragment = CategoryFragment()
    val educationalFragment: Fragment = EducationalFragment()
    val fm = supportFragmentManager
    var activeFragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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



}
