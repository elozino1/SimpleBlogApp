package com.olamachia.simpleblogapp.repository

import com.olamachia.simpleblogapp.models.CommentResponseItem
import com.olamachia.simpleblogapp.models.ResponseItem
import com.olamachia.simpleblogapp.services.RetrofitInstance

class PostsRepository {

    suspend fun getPosts() = RetrofitInstance.api.getPosts()

    suspend fun getComments(userId: Int) = RetrofitInstance.api.getComments(userId)

    suspend fun searchPosts(queryString: String) = RetrofitInstance.api.searchPosts(queryString)

    suspend fun addPost(post: ResponseItem) = RetrofitInstance.api.addPost(post)

    suspend fun addNewComment(comment: CommentResponseItem) = RetrofitInstance.api.addComment(comment)
}