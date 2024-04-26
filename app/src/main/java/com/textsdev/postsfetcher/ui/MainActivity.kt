package com.textsdev.postsfetcher.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.textsdev.postsfetcher.databinding.ActivityMainBinding
import com.textsdev.postsfetcher.model.PostsModel
import com.textsdev.postsfetcher.ui.adapter.MainAdapter
import com.textsdev.postsfetcher.ui.viewmodel.MainViewModel
import com.textsdev.postsfetcher.ui.views.BottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private var currentPage = 1
    private lateinit var inflate: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate = ActivityMainBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        recyclerView = inflate.postsRv
        adapter = MainAdapter {
            //No api callbacks are needed to show comments data as its already fetched and stored
            val bottomSheetFragment = BottomSheet.newInstance(
                it.title, it.body, viewModel.getCommentsOfPost(it.id).toList()
            )
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        observeData()

        viewModel.fetchPosts(currentPage)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val visibleThreshold = 5 // Adjust as needed
                val loading: Boolean = viewModel.uiState.value?.isLoading ?: false
                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    currentPage++
                    viewModel.fetchPosts(currentPage)
                }
            }
        })
    }

    private fun observeData() {
        viewModel.postsData.observe(this) { newData ->
            updateData(newData)
        }
        viewModel.uiState.observe(this) { uiState ->
            if (uiState.isLoading) {
                inflate.loaderTv.visibility = VISIBLE
            } else {
                inflate.loaderTv.visibility = GONE
            }
        }
    }

    private fun updateData(newData: List<PostsModel>) {
        adapter.addData(newData)
        viewModel.hideLoader()
    }
}