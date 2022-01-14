package com.olamachia.simpleblogapp.services

import com.olamachia.simpleblogapp.models.CommentResponseItem
import com.olamachia.simpleblogapp.models.ResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostsService {

    //end-point to retrieve all posts
    @GET("/posts")
    suspend fun getPosts(): Response<List<ResponseItem>>

    //end-point to retrieve the comments made on a specific post
    @GET("/comments")
    suspend fun getComments(
        @Query("postId") postId: Int
    ): Response<List<CommentResponseItem>>

    //end-point to filter posts
    @GET("/posts")
    suspend fun searchPosts(
        @Query("q") queryString: String
    ) : Response<List<ResponseItem>>

    //end-point to create a post
    @POST("/posts")
    suspend fun addPost(
        @Body post: ResponseItem
    ) : Response<ResponseItem>

    //end-point to add a comment to a post
    @POST("/comments")
    suspend fun addComment(
        @Body comment: CommentResponseItem
    ) : Response<CommentResponseItem>
}