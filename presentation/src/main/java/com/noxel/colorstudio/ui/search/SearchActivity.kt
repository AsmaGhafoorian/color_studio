package com.noxel.colorstudio.ui.search

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.noxel.colorstudio.*
import com.noxel.colorstudio.model.CategoryModel
import com.noxel.colorstudio.model.SearchModel
import com.noxel.colorstudio.model.SearchRequestModel
import com.noxel.colorstudio.ui.base.BaseActivity
import com.noxel.colorstudio.ui.main.category.CategoryAdapter
import com.noxel.colorstudio.ui.main.category.CategoryViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_category.*
import okhttp3.RequestBody

class SearchActivity : BaseActivity(){

    var mSearchAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        getAppInjector().inject(this)
        btn_back.setOnClickListener { finish() }
        configureSearchView()
    }

    private fun configureSearchView(){
        search_view
                .apply {
                    setIconifiedByDefault(false)
                    requestFocus()
                    setOnQueryTextListener(
                            DebouncingQueryTextListener(
                                    this@SearchActivity.lifecycle
                            ) { newText ->
                                newText?.let {
                                    if (it.isEmpty()) {
                                        mSearchAdapter?.clear()
                                    } else {
                                        postSearches(SearchRequestModel(it, null, null))

                                    }
                                }
                            }
                    )
                }
    }



    private fun configureRecyclerView(searchList: MutableList<SearchModel>) {

        mSearchAdapter = SearchAdapter(searchList, this, selectedSearchItem)
        rv_search.layoutManager = LinearLayoutManager(this)

        rv_search.itemAnimator = DefaultItemAnimator()
        rv_search.adapter = mSearchAdapter


//        mSearchAdapter!!.click = object : OnProductClick {
//            override fun onProductClick(image: String?, detail: String?, title: String?, colorCode: String?) {
//                val intent = Intent(this@SearchActivity, DetailActivity::class.java)
//                intent.putExtra(PRODUCR_IMAGE, image)
//                intent.putExtra(PRODUCT_DETAIL, detail)
//                intent.putExtra(PRODUCT_TITLE, title)
//                intent.putExtra(PRODUCT_COLOR_CODE, colorCode)
//                startActivity(intent)
//            }
//
//        }

    }


    var selectedSearchItem : (SearchModel) -> Unit ={

    }
    fun postSearches(requestBody:  SearchRequestModel){
        withViewModel<SearchViewModel>(viewModelFactory){
            postSerches(true, requestBody , this@SearchActivity)
            observe(search, ::getSearchResponse)
        }
    }

    private fun getSearchResponse(data: Data<MutableList<SearchModel>>?) {
        data?.let {

            when (it.dataState) {

                DataState.LOADING -> {
                    Log.d("=========>", "Loading")

                }
                DataState.SUCCESS -> {
                    Log.d("=========>", "Success")
                    it.data?.let { it1 -> configureRecyclerView(it1) }
                }
                DataState.ERROR -> {
                }
            }
        }
    }

}