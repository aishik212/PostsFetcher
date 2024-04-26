package com.textsdev.postsfetcher.api

import com.textsdev.postsfetcher.model.CommentsModel
import com.textsdev.postsfetcher.model.PostsModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts/{postId}")
    suspend fun getPosts(@Path("postId") postId: Int): PostsModel

    @GET("posts/{postId}/comments")
    suspend fun getComments(@Path("postId") postId: Int): CommentsModel
}