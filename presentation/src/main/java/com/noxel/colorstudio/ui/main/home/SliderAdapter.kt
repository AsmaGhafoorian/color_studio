package com.noxel.colorstudio.ui.main.home

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noxel.colorstudio.R
import com.noxel.colorstudio.loadRectRoundImage
import com.noxel.colorstudio.model.SliderModel
import kotlinx.android.synthetic.main.adapter_slider.view.*
import java.util.ArrayList

class SliderAdapter (val selectedSlid : (SliderModel) -> Unit) : PagerAdapter() {

    lateinit var parentActivity : Activity
    lateinit var mLayoutInflater: LayoutInflater
    private var slides= ArrayList<SliderModel>()

    override fun getCount(): Int {
        return slides.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.adapter_slider, container, false)



        slides[position].let { it.image?.let { it1 -> itemView.sliderImage.loadRectRoundImage(it1) } }
        itemView.setOnClickListener {
            selectedSlid.invoke(slides[position])
        }

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    /*for set view pager items margin to show a little part of another item in view*/
    override fun getPageWidth(position: Int): Float {

        return 0.96F
    }

    fun setItems(items: List<SliderModel>, activity: Activity) {

        parentActivity = activity
        mLayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        slides.clear()
        items?.let { slides.addAll(it) }
        notifyDataSetChanged()
    }
}
