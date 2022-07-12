package com.example.qiita_clone_android.ui

import androidx.lifecycle.ViewModel
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.articleList.ArticleListFragment

class MainViewModel: ViewModel() {
    var currentFragment: BaseFragment = ArticleListFragment()
        private set

    var articles: List<Article> = arrayListOf()
        private set

    var selectedArticle: Article? = null
        private set

    fun setCurrentFragment(fragment: BaseFragment) {
        this.currentFragment = fragment
    }

    fun setArticles(articles: List<Article>) {
        this.articles = articles
    }

    fun setSelectedArticle(article: Article) {
        this.selectedArticle = article
    }
}