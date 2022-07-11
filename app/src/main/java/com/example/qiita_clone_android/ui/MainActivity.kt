package com.example.qiita_clone_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.articleList.ArticleListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),
    ArticleListFragment.ArticlesActions {

    private var currentFragment: BaseFragment = ArticleListFragment()
    var selectedArticle: Article? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolBar()
        setBottomNavView()

        transitionTo(currentFragment)
    }

    private fun transitionTo(to: BaseFragment) {
        currentFragment = to
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_content, currentFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setToolBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = ""

        supportFragmentManager.addOnBackStackChangedListener {
            currentFragment =
                supportFragmentManager.findFragmentById(R.id.main_activity_content) as BaseFragment
        }
    }

    private fun setBottomNavView() {
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.article_list_tab -> {
                    transitionTo(ArticleListFragment())
                }
                R.id.article_favorite_tab -> {
                    transitionTo(WebViewFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    // ArticleListFragment.ArticlesActions

    override fun onTapArticle(article: Article) {
        selectedArticle = article
        transitionTo(WebViewFragment.newInstance(article.url))
    }
}