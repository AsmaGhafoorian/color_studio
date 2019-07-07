package com.noxel.colorstudio.ui.main.sub_category

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.noxel.colorstudio.R
import com.noxel.colorstudio.inflate
import com.noxel.colorstudio.loadRectRoundImage
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.model.SubCategoryModel
import kotlinx.android.synthetic.main.adapter_subcategory_horizontal.view.*

class SubCategoryHorizontalAdapter (val categories: List<SubCategoryModel>,
                                    val parentActivity: Activity,
                                    val selectedCategory : (SubCategoryModel) -> Unit
) : RecyclerView.Adapter<SubCategoryHorizontalAdapter.ViewHolder>(){

    override fun getItemCount(): Int = categories.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(categories[position], position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)


    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.adapter_subcategory_horizontal)) {

        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(category: SubCategoryModel, position: Int) {

            category.image?.let { itemView.image.loadRectRoundImage(it) }
            itemView.title.text = category.title

            if (position == 0){
                itemView.isSelected = true
            }
            itemView.setOnClickListener {
                selectedCategory.invoke(category)
            }

        }
    }
}