package com.noxel.colorstudio.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.noxel.colorstudio.R
import com.noxel.colorstudio.inflate
import com.noxel.colorstudio.loadRectRoundImage
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.model.SearchModel
import com.noxel.colorstudio.ui.main.category.CategoryAdapter
import kotlinx.android.synthetic.main.adapter_category.view.*

class SearchAdapter (private var searcheItems: MutableList<SearchModel>,
                     val parentActivity: Activity,
                     val selectedSearchItem : (SearchModel) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun getItemCount(): Int = searcheItems.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(searcheItems[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)


    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.adapter_search)) {

        var uri = ""
        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(searchItem: SearchModel) {


        }
    }


    fun clear() {
        searcheItems.clear()
        notifyDataSetChanged()
    }
}