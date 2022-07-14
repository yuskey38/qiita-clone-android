package com.example.qiita_clone_android.ui

import androidx.lifecycle.ViewModel
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.articleList.ArticleListFragment

class MainViewModel: ViewModel() {
    var currentFragment: BaseFragment = ArticleListFragment()
        private set

    var articles: List<Article> = emptyList()
        private set

    fun transitionTo(fragment: BaseFragment) {
        this.currentFragment = fragment
    }

    fun onDestroyArticleListView(articles: List<Article>) {
        this.articles = articles
    }
}