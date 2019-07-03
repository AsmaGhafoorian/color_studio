package com.noxel.colorstudio.ui.main.sub_category

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.noxel.colorstudio.R
import com.noxel.colorstudio.inflate
import com.noxel.colorstudio.loadRectRoundImage
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.ui.main.category.CategoryAdapter
import com.noxel.colorstudio.utils.CONSUMER
import com.noxel.colorstudio.utils.COSTUMER
import kotlinx.android.synthetic.main.adapter_subcategory_vertical.view.*

class SubCategoryVerticalAdapter (val products: MutableList<ProductModel>,
                                  val parentActivity: Activity,
                                  val selectedCategory : (ProductModel) -> Unit,
                                  val userType: Int,
                                  val screenHeight: Int
                            ) : RecyclerView.Adapter<SubCategoryVerticalAdapter.ViewHolder>(){

    override fun getItemCount(): Int = products.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)


    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.adapter_subcategory_vertical)) {

        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(product: ProductModel) {

            itemView.image.layoutParams.height = screenHeight
            product.image?.let { itemView.image.loadRectRoundImage(it) }

            when(userType){
                COSTUMER -> {
                    itemView.title.text = product.title ?: " "
                }

                CONSUMER -> {
                    itemView.title.text = parentActivity.getString(R.string.number_title) + " " + (product.color_code ?: " ")
                }
            }


            itemView.setOnClickListener {
                selectedCategory.invoke(product)
            }
        }
    }
    
    fun clear(){
        products.clear()
        notifyDataSetChanged()
    }

}