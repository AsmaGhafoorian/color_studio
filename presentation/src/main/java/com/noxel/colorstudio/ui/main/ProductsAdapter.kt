package com.noxel.colorstudio.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.noxel.colorstudio.inflate
import com.noxel.colorstudio.loadRectRoundImage
import com.noxel.colorstudio.model.ProductModel
import kotlinx.android.synthetic.main.adapter_products.view.*
import android.util.DisplayMetrics
import com.noxel.colorstudio.R
import com.noxel.colorstudio.utils.CONSUMER
import com.noxel.colorstudio.utils.COSTUMER


class ProductsAdapter (val productsList: List<ProductModel>,
                       val parentActivity: Activity,
                       val selectedProduct : (ProductModel) -> Unit,
                       val userType: Int) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>(){

    override fun getItemCount(): Int = productsList.count()

    override fun onBindViewHolder(holder: ProductsAdapter.ViewHolder, position: Int) = holder.bind(productsList[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.ViewHolder = ViewHolder(parent)


    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.adapter_products)) {

        var uri = ""
        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(product: ProductModel) {

            val displayMetrics = DisplayMetrics()
            parentActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels/2.6
            val height = width

            itemView.productName.layoutParams.width = width.toInt()
            itemView.productImg.layoutParams.height = width.toInt()
            itemView.productImg.layoutParams.width = height.toInt()

            product.image?.let { itemView.productImg.loadRectRoundImage(it) }

            when(userType){
                COSTUMER -> {
                    itemView.productName.text = product.title
                }
                CONSUMER -> {
                    itemView.productName.text = parentActivity.getString(R.string.number_title) + " " + product.color_code
                }
            }
            itemView.setOnClickListener {
                selectedProduct.invoke(product)
            }
        }
    }
}