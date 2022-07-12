package com.example.qiita_clone_android.ui.articleFavoriteList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qiita_clone_android.data.local.dao.article.ArticleFavoriteDao
import com.example.qiita_clone_android.models.Article

class ArticleFavoriteListViewModel : ViewModel() {

    private var _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    fun loadArticles() {
        val dao = ArticleFavoriteDao()
        _articles.postValue(dao.findAll())
    }
}
