package com.example.qiita_clone_android.ui.articleList

import androidx.lifecycle.*
import com.example.qiita_clone_android.data.repository.ArticleRepository
import com.example.qiita_clone_android.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleListViewModel(
    private val repository: ArticleRepository
) : ViewModel() {

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

    class Factory(
        private val repository: ArticleRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticleListViewModel(repository) as T
        }
    }
}