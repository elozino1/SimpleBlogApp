package com.olamachia.simpleblogapp.services

import com.olamachia.simpleblogapp.models.CommentResponseItem
import com.olamachia.simpleblogapp.models.ResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostsService {

    @GET("/posts")
    suspend fun getPosts(): Response<List<ResponseItem>>

    @GET("/comments")
    suspend fun getComments(
        @Query("postId") postId: Int
    ): Response<List<CommentResponseItem>>

    @GET("/posts")
    suspend fun searchPosts(
        @Query("queryString") queryString: String
    ) : Response<List<ResponseItem>>

    @POST()
    suspend fun postComment()
}