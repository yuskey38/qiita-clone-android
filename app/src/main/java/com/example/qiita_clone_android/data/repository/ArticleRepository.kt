package com.example.qiita_clone_android.data.repository

import com.example.qiita_clone_android.data.local.dao.article.ArticleFavoriteDao
import com.example.qiita_clone_android.data.local.entity.ArticleFavoriteEntity
import com.example.qiita_clone_android.data.remote.ApiClient.retrofit
import com.example.qiita_clone_android.data.remote.ApiRequest
import com.example.qiita_clone_android.models.Article
import retrofit2.Response

class ArticleRepository {

    private val articleFavoriteDao: ArticleFavoriteDao = ArticleFavoriteDao()

    fun fetchArticles(query: String?): List<Article> {
        return retrofit.create(ApiRequest::class.java)
            .fetchArticles(1, 20, query)
            .execute()
            .body() ?: listOf()
    }

    fun getAllFavorites(): List<Article> {
        return articleFavoriteDao.findAll()
    }

    fun getFavoriteBy(id: String): Article? {
        return articleFavoriteDao.findBy(id)
    }

    fun addFavorite(article: Article) {
        articleFavoriteDao.insert(article)
    }

    fun removeFavorite(article: Article) {
        articleFavoriteDao.delete(article)
    }

    fun clearFavorites() {
        articleFavoriteDao.clear()
    }
}