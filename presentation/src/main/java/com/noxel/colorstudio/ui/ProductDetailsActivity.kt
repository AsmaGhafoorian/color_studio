package com.noxel.colorstudio.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.noxel.colorstudio.R
import com.noxel.colorstudio.loadRectRoundImage
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.ui.base.BaseActivity
import com.noxel.colorstudio.utils.CONSUMER
import com.noxel.colorstudio.utils.COSTUMER
import com.noxel.colorstudio.utils.PRODUCT
import kotlinx.android.synthetic.main.activity_product_details.*
import java.util.function.Consumer

class ProductDetailsActivity : BaseActivity(){


    var product : ProductModel? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        registerToolbar()
        product = intent.getSerializableExtra(PRODUCT) as ProductModel

        //    var userType = sharedPreference.retrievedUserType(this)
        var userType = CONSUMER
        when(userType){
            COSTUMER -> {
                tv.text = product?.description_customer ?: " "
            }
            CONSUMER -> {
                tv.text = product?.description_salon ?: " "
                if(product?.title != null && !product?.color_code.isNullOrEmpty())
                    detail_title.text =  product?.title  +  " " + getString(R.string.number_title) + " " + product?.color_code

                else
                    detail_title.text =  product?.title
            }
        }

        product?.image?.let { img.loadRectRoundImage(it) }

    }
}