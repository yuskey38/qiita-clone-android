package com.example.qiita_clone_android.ui.articleFavoriteList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qiita_clone_android.data.local.dao.article.ArticleFavoriteDao
import com.example.qiita_clone_android.data.repository.ArticleListRepository
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.models.QiitaUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleFavoriteListViewModel : ViewModel() {

    private var _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    fun loadArticles() {
        val dao = ArticleFavoriteDao()
        _articles.postValue(dao.findAll())
    }
}
