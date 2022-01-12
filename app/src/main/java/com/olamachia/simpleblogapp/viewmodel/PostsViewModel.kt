package com.olamachia.simpleblogapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olamachia.simpleblogapp.models.CommentResponseItem
import com.olamachia.simpleblogapp.models.ResponseItem
import com.olamachia.simpleblogapp.repository.PostsRepository
import com.olamachia.simpleblogapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PostsViewModel(
    val postsRepository: PostsRepository
) : ViewModel() {

    val allPosts: MutableLiveData<Resource<List<ResponseItem>>> = MutableLiveData()
    val allComments: MutableLiveData<Resource<List<CommentResponseItem>>> = MutableLiveData()

    init {
        getPosts()
    }

    private fun getPosts() = viewModelScope.launch(Dispatchers.IO) {
        allPosts.postValue(Resource.Loading())
        val result = postsRepository.getPosts()
        allPosts.postValue(handlePostsResponse(result))
    }

    fun getComments(postId: Int) = viewModelScope.launch(Dispatchers.IO) {
        allComments.postValue(Resource.Loading())
        val commentsResult = postsRepository.getComments(postId)
        allComments.postValue(handleCommentsResponse(commentsResult))
    }

    private fun handlePostsResponse(result: Response<List<ResponseItem>>): Resource<List<ResponseItem>>? {
        if(result.isSuccessful) {
            result.body()?.let { postsResult ->
                return Resource.Success(postsResult)
            }
        }
        return Resource.Error(result.message())
    }

    private fun handleSearchPostsResponse(result: Response<List<ResponseItem>>): Resource<List<ResponseItem>>? {
        if(result.isSuccessful) {
            result.body()?.let { postsResult ->
                return Resource.Success(postsResult)
            }
        }
        return Resource.Error(result.message())
    }

    private fun handleCommentsResponse(result: Response<List<CommentResponseItem>>): Resource<List<CommentResponseItem>>? {
        if(result.isSuccessful) {
            result.body()?.let { commentsResult ->
                return Resource.Success(commentsResult)
            }
        }
        return Resource.Error(result.message())
    }

}