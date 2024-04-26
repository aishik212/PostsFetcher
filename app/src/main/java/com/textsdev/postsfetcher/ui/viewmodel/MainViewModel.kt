package com.textsdev.postsfetcher.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.textsdev.postsfetcher.api.ApiService
import com.textsdev.postsfetcher.model.CommentsModel
import com.textsdev.postsfetcher.model.PostsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

data class UIState(
    val isLoading: Boolean = false,
    val isError: String? = null,
)

@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _postsData = MutableLiveData<List<PostsModel>>()
    val postsData: LiveData<List<PostsModel>> get() = _postsData

    private val _commentsData = mutableListOf<CommentsModel.CommentsModelItem>()

    private val _uiState: MutableLiveData<UIState> = MutableLiveData(UIState())
    val uiState: MutableLiveData<UIState> = _uiState

    fun fetchPosts(page: Int) {
        showLoader()
        clearError()
        CoroutineScope(Dispatchers.IO).launch {
            // Simulate fetching data from an API
            val newData = mutableListOf<PostsModel>()
            val loadLimit = 10
            for (i in (page - 1) * loadLimit until page * loadLimit) {
                try {
                    newData.add(apiService.getPosts(i + 1))
                    fetchComments(i + 1)
                } catch (e: Exception) {
                    showError("Unable to fetch posts")
                    Log.e("ERROR", "fetchPostsError : ${e.localizedMessage}")
                }
            }
            _postsData.postValue(newData)
        }
    }

    private fun fetchComments(postId: Int) {
        //Load the comments in background and add it to a list
        viewModelScope.launch {
            try {
                val time = measureTimeMillis {
                    val toMutableList = apiService.getComments(postId).toMutableList()
                    _commentsData.addAll(toMutableList)
                }
                delay(200)
                //This log will output the time taken for each comment fetch
                Log.d("TIME_LOG", "Time Taken to Fetch Each comment -> $time ms")
            } catch (e: Exception) {
                Log.e("ERROR", "fetchCommentsError : ${e.localizedMessage}")
            }
        }
    }

    fun showError(e: String) {
        _uiState.postValue(_uiState.value?.copy(isError = e))
    }

    fun clearError() {
        _uiState.postValue(_uiState.value?.copy(isError = null))
    }

    fun hideLoader() {
        _uiState.value = _uiState.value?.copy(isLoading = false)
    }

    private fun showLoader() {
        _uiState.value = _uiState.value?.copy(isLoading = true)
    }

    fun getCommentsOfPost(id: Int): List<CommentsModel.CommentsModelItem> {
        //Get Comments from cached data
        return _commentsData.filter { commentsModel ->
            commentsModel.postId == id
        }
    }
}