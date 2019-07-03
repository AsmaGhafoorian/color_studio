package com.noxel.colorstudio.ui.main.category

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noxel.colorstudio.*
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.navigation.Navigator
import com.noxel.colorstudio.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_category.*
import javax.inject.Inject

class CategoryFragment : BaseFragment(){

   

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("=========>", "CategoryFragment")
        activity!!.getAppInjector().inject(this)
        return inflater.inflate(R.layout.fragment_category, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCategories()

        swiperefresh.setOnRefreshListener { getCategories() }

        btn_search.setOnClickListener {
            navigator.navigateToSearchActivity(activity!!)
        }
    }


    /* get Categories list -----------------------------------------------*/

    private val selectedCategory: (CategoryModel) -> Unit = {

        it.id?.let { it1 -> activity?.let { it2 -> navigator.navigateToSubCategoryActivity(it2, it1) } }
    }


    fun getCategories(){
        activity?.withViewModel<CategoryViewModel>(viewModelFactory){
            getCategories(true, activity!!)
            observe(categories, ::getCategoriesResponse)
        }
    }

    private fun getCategoriesResponse(data: Data<List<CategoryModel>>?) {
        data?.let {

            when (it.dataState) {

                DataState.LOADING -> {
                    Log.d("=========>", "Loading")

                }
                DataState.SUCCESS -> {
                    Log.d("=========>", "Success")
                    categoryRecycler.adapter = it.data?.let { it1 -> activity?.let { it2 -> CategoryAdapter(it1, it2, selectedCategory) } }
                    categoryRecycler.setLayoutManager(GridLayoutManager(activity, 2))
                }
                DataState.ERROR -> {
                }
            }
        }
    }

}