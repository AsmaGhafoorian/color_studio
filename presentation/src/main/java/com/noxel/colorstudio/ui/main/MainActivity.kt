package com.noxel.colorstudio.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log
import com.noxel.colorstudio.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.noxel.colorstudio.*
import com.noxel.colorstudio.ui.main.category.CategoryFragment
import com.noxel.colorstudio.ui.main.educaional.EducationalFragment
import com.noxel.colorstudio.ui.main.home.HomeFragment
import com.noxel.colorstudio.ui.main.profile.ProfileFragment


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

        fm.beginTransaction().add(R.id.main_container, homeFragment, "1").commit()
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

                    R.id.navigation_center ->{
                       askCameraPermission()
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

    fun askCameraPermission(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?, token: PermissionToken?) {

                        AlertDialog.Builder(this@MainActivity)
                                .setTitle(
                                        "Permissions Error!")
                                .setMessage(
                                        "Please allow permissions to take photo with camera")
                                .setNegativeButton(
                                        android.R.string.cancel
                                ) { dialog, _ ->
                                    dialog.dismiss()
                                    token?.cancelPermissionRequest()
                                }
                                .setPositiveButton(android.R.string.ok
                                ) { dialog, _ ->
                                    dialog.dismiss()
                                    token?.continuePermissionRequest()
                                }
                                .setOnDismissListener {
                                    token?.cancelPermissionRequest() }
                                .show()

                    }

                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {/* ... */
                        if(report.areAllPermissionsGranted()){
                            //once permissions are granted, launch the camera
                            navigator.navigateToMagicMirrorActivity(this@MainActivity)
                        }else{
                            Toast.makeText(this@MainActivity, "All permissions need to be granted to take photo", Toast.LENGTH_LONG).show()
                        }
                    }


                }).check()
    }

}
