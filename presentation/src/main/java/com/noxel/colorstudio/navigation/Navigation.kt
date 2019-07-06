package com.noxel.colorstudio.navigation

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.ui.ProductDetailsActivity
import com.noxel.colorstudio.ui.magic_mirror.MagicMirrorActivity
import com.noxel.colorstudio.ui.main.sub_category.SubCategoryActivity
import com.noxel.colorstudio.ui.search.SearchActivity
import com.noxel.colorstudio.utils.PRODUCT
import com.noxel.colorstudio.utils.SUB_CAT_ID
import javax.inject.Inject


class Navigator @Inject constructor() {
    fun navigateToSearchActivity(activity: Activity) {
        val intent = Intent(activity, SearchActivity::class.java)
        activity.startActivity(intent)
    }


    fun navigateToSubCategoryActivity(activity: Activity, catId: Int) {
        val intent = Intent(activity, SubCategoryActivity::class.java)
        intent.putExtra(SUB_CAT_ID, catId)
        activity.startActivity(intent)
    }

    fun navigateToProductDetailsActivity(activity: Activity, product: ProductModel) {
        val intent = Intent(activity, ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT, product)
        activity.startActivity(intent)
    }

    fun navigateToMagicMirrorActivity(activity: Activity){
        val intent = Intent(activity, MagicMirrorActivity::class.java)
        activity.startActivity(intent)
    }
}




