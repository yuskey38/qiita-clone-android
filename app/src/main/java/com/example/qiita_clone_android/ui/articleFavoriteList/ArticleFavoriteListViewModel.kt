package com.example.qiita_clone_android.ui.articleFavoriteList

import androidx.lifecycle.*
import com.example.qiita_clone_android.data.local.dao.article.ArticleFavoriteDao
import com.example.qiita_clone_android.data.repository.ArticleRepository
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.articleList.ArticleListViewModel
import kotlinx.coroutines.launch

class ArticleFavoriteListViewModel(
    private val repository: ArticleRepository
) : ViewModel() {

    private var _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    fun onResume() {
        viewModelScope.launch {
            val articles = repository.getAllFavorites()
            _articles.value = articles
        }
    }

    class Factory(
        private val repository: ArticleRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticleFavoriteListViewModel(repository) as T
        }
    }
}
