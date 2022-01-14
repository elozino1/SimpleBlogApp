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
    val sentPosts: MutableLiveData<Resource<ResponseItem>> = MutableLiveData()
    val sentComments: MutableLiveData<Resource<CommentResponseItem>> = MutableLiveData()

    init {
        getPosts()
    }

    //fetch all posts
    private fun getPosts() = viewModelScope.launch(Dispatchers.IO) {
        allPosts.postValue(Resource.Loading())
        val result = postsRepository.getPosts()
        allPosts.postValue(handlePostsResponse(result))
    }

    //get all comments added to a particular post
    fun getComments(postId: Int) = viewModelScope.launch(Dispatchers.IO) {
        allComments.postValue(Resource.Loading())
        val commentsResult = postsRepository.getComments(postId)
        allComments.postValue(handleCommentsResponse(commentsResult))
    }

    //add a new post
    fun addPost(post: ResponseItem) = viewModelScope.launch(Dispatchers.IO) {
        sentPosts.postValue(Resource.Loading())
        val result = postsRepository.addPost(post)
        sentPosts.postValue(handleAddPost(result))
    }

    //add a new comment to a post
    fun addNewComment(comment: CommentResponseItem) = viewModelScope.launch(Dispatchers.IO){
        sentComments.postValue(Resource.Loading())
        val result = postsRepository.addNewComment(comment)
        sentComments.postValue(handleAddCommentResponse(result))
    }

    //handle the response from adding a comment to a post
    private fun handleAddCommentResponse(result: Response<CommentResponseItem>): Resource<CommentResponseItem>? {
        if(result.isSuccessful) {
            result.body()?.let { sentCommentResponse ->
                return Resource.Success(sentCommentResponse)
            }
        }
        return Resource.Error(result.message())
    }

    //handle the response from creating a new post
    private fun handleAddPost(result: Response<ResponseItem>): Resource<ResponseItem>? {
        if(result.isSuccessful) {
            result.body()?.let { sentPostResponse ->
                return Resource.Success(sentPostResponse)
            }
        }
        return Resource.Error(result.message())
    }

    //handle the response from getting all posts from api call
    private fun handlePostsResponse(result: Response<List<ResponseItem>>): Resource<List<ResponseItem>>? {
        if(result.isSuccessful) {
            result.body()?.let { postsResult ->
                return Resource.Success(postsResult)
            }
        }
        return Resource.Error(result.message())
    }

    //handle the response from getting comments from api call
    private fun handleCommentsResponse(result: Response<List<CommentResponseItem>>): Resource<List<CommentResponseItem>>? {
        if(result.isSuccessful) {
            result.body()?.let { commentsResult ->
                return Resource.Success(commentsResult)
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



    fun addNewPost(postContent: ResponseItem) {
        addPost(postContent)
    }

}