package com.example.qiita_clone_android.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.articleFavoriteList.ArticleFavoriteListFragment
import com.example.qiita_clone_android.ui.articleList.ArticleListFragment
import com.example.qiita_clone_android.ui.articleList.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),
    ArticleListFragment.ArticlesActions {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolBar()
        setBottomNavView()

        transitionTo(viewModel.currentFragment)
    }

    private fun transitionTo(to: BaseFragment) {
        viewModel.setCurrentFragment(to)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_content, viewModel.currentFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setToolBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = ""

        supportFragmentManager.addOnBackStackChangedListener {
            viewModel.setCurrentFragment(
                supportFragmentManager.findFragmentById(R.id.main_activity_content) as BaseFragment)
        }
    }

    private fun setBottomNavView() {
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavView.setOnItemSelectedListener { item ->
            var count = supportFragmentManager.backStackEntryCount
            while (count > 1) {
                supportFragmentManager.popBackStack()
                count -= 1
            }

            when (item.itemId) {
                R.id.article_list_tab -> {
                    transitionTo(ArticleListFragment())
                }
                R.id.article_favorite_tab -> {
                    transitionTo(ArticleFavoriteListFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    // ArticleListFragment.ArticlesActions

    override fun onTapArticle(article: Article) {
        viewModel.setSelectedArticle(article)
        transitionTo(WebViewFragment.newInstance(article.url))
    }

    override fun saveArticles(articles: List<Article>) {
        viewModel.setArticles(articles)
    }
}