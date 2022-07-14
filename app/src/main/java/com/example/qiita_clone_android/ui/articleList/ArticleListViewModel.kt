package com.example.qiita_clone_android.ui.articleList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qiita_clone_android.data.repository.ArticleRepository
import com.example.qiita_clone_android.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleListViewModel : ViewModel() {
    private val repository = ArticleRepository()

    private var _articles = MutableLiveData<List<Article>>(emptyList())
    val articles: LiveData<List<Article>>
        get() = _articles

    private var query: String? = null

    fun fragmentIsReadyWith(articles: List<Article>) {
        setArticles(articles)
    }

    fun fragmentIsReady() {
        fetchArticles()
    }

    fun onQueryChange(query: String) {
        this.query = query
    }

    fun onQuerySubmit() {
        fetchArticles()
    }

    fun pullToRefresh() {
        fetchArticles()
    }

    private fun setArticles(articles: List<Article>?) {
        _articles.value = articles
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            val articles = repository.fetchArticles(query)
            setArticles(articles)
        }
    }
}
