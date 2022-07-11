package com.example.qiita_clone_android.ui.articleList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qiita_clone_android.data.repository.ArticleListRepository
import com.example.qiita_clone_android.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleListViewModel : ViewModel() {
    private val repository = ArticleListRepository()

    private var _articles = MutableLiveData<List<Article>>(listOf())
    val articles: LiveData<List<Article>>
        get() = _articles

    private var query: String? = null

    fun setArticles(articles: List<Article>?) {
        if (articles.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                _articles.postValue(repository.fetchArticles(query))
            }
        } else {
            _articles.postValue(articles)
        }
    }

    fun updateQuery(query: String) {
        this.query = query
    }
}
