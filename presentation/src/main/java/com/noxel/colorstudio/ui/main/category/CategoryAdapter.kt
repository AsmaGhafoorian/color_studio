package com.noxel.colorstudio.ui.main.category

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.ViewGroup
import com.noxel.colorstudio.R
import com.noxel.colorstudio.inflate
import com.noxel.colorstudio.loadRectRoundImage
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.ui.main.home.ProductsAdapter
import com.noxel.colorstudio.utils.CONSUMER
import com.noxel.colorstudio.utils.COSTUMER
import kotlinx.android.synthetic.main.adapter_category.view.*
import kotlinx.android.synthetic.main.adapter_products.view.*

class CategoryAdapter (val categories: List<CategoryModel>,
                       val parentActivity: Activity,
                       val selectedCategory : (CategoryModel) -> Unit
                       ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    override fun getItemCount(): Int = categories.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(categories[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)


    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.adapter_category)) {

        var uri = ""
        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(category: CategoryModel) {

            category.image?.let { itemView.image.loadRectRoundImage(it) }
            itemView.categoryTitle.text = category.title

            itemView.setOnClickListener {
                selectedCategory.invoke(category)
            }
        }
    }
}