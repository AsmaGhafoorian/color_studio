package com.noxel.colorstudio.ui.magic_mirror

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.noxel.colorstudio.R
import com.noxel.colorstudio.inflate
import com.noxel.colorstudio.loadCircleAvatar
import com.noxel.colorstudio.model.SubCategoryModel
import kotlinx.android.synthetic.main.adapter_color_category.view.*

class ColorCategoryAdapter (val categories: List<SubCategoryModel>,
                            val parentActivity: Activity,
                            val selectedCategory : (SubCategoryModel) -> Unit
) : RecyclerView.Adapter<ColorCategoryAdapter.ViewHolder>(){

    override fun getItemCount(): Int = categories.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(categories[position], position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)


    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.adapter_color_category)) {

        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(category: SubCategoryModel, position: Int) {

            category.image?.let { itemView.colorImage.loadCircleAvatar(it) }
            itemView.colorName.text = category.title ?: " "

            itemView.setOnClickListener {
                selectedCategory.invoke(category)
            }

        }
    }
}