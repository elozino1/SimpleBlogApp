package com.olamachia.simpleblogapp.repository

import com.olamachia.simpleblogapp.services.RetrofitInstance

class PostsRepository {

    suspend fun getPosts() = RetrofitInstance.api.getPosts()

    suspend fun getComments(userId: Int) = RetrofitInstance.api.getComments(userId)

    suspend fun searchPosts(queryString: String) = RetrofitInstance.api.searchPosts(queryString)
}