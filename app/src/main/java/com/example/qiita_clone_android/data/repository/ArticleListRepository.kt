package com.example.qiita_clone_android.data.repository

import com.example.qiita_clone_android.data.remote.ApiClient.retrofit
import com.example.qiita_clone_android.data.remote.ApiRequest
import com.example.qiita_clone_android.models.Article

class ArticleListRepository {
    fun fetchArticles(tag: String, page: Int): List<Article> {
        return retrofit.create(ApiRequest::class.java)
            .fetchArticles(tag, page, 20)
            .body()
            .orEmpty()
    }
}