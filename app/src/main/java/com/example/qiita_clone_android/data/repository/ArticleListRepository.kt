package com.example.qiita_clone_android.data.repository

import com.example.qiita_clone_android.data.remote.ApiClient.retrofit
import com.example.qiita_clone_android.data.remote.ApiRequest
import com.example.qiita_clone_android.models.Article
import retrofit2.Response

class ArticleListRepository {
    fun fetchArticles(page: Int): List<Article> {
        return retrofit.create(ApiRequest::class.java)
            .fetchArticles(page, 20)
            .execute()
            .body() ?: listOf()
    }
}