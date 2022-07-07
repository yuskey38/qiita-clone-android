package com.example.qiita_clone_android.data.remote

import com.example.qiita_clone_android.models.Article
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequest {
    @GET("tags/{tag_id}/items")
    fun fetchArticles(
        @Path("tag_id") tagId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<Article>>
}