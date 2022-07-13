package com.example.qiita_clone_android.data.remote

import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {
    @GET("/api/v2/items")
    fun fetchArticles(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("query") query: String?,
    ): Call<List<Article>>

    @GET("/api/v2/users")
    fun fetchUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Call<List<User>>
}