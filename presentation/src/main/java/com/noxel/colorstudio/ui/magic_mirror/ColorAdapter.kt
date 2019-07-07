package com.noxel.colorstudio.ui.magic_mirror

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.noxel.colorstudio.R
import com.noxel.colorstudio.inflate
import com.noxel.colorstudio.loadCircleAvatar
import com.noxel.colorstudio.loadRectRoundImage
import com.noxel.colorstudio.model.ProductModel
import com.noxel.colorstudio.ui.main.sub_category.SubCategoryVerticalAdapter
import com.noxel.colorstudio.utils.CONSUMER
import com.noxel.colorstudio.utils.COSTUMER
import kotlinx.android.synthetic.main.adapter_color_category.view.*
import kotlinx.android.synthetic.main.adapter_subcategory_vertical.view.*

class ColorAdapter (val colors: MutableList<ProductModel>,
                    val parentActivity: Activity,
                    val selectedColor : (ProductModel) -> Unit
                ) : RecyclerView.Adapter<ColorAdapter.ViewHolder>(){

    override fun getItemCount(): Int = colors.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(colors[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)


    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.adapter_color_category)) {

        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(color: ProductModel) {

            color.image?.let { itemView.colorImage.loadCircleAvatar(it) }
            itemView.colorName.text = color.title ?: " "

            itemView.colorName.setTextColor(parentActivity.getColor(R.color.gray5))
            itemView.setOnClickListener {
                selectedColor.invoke(color)
            }
        }
    }
}