package com.example.qiita_clone_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.ui.articleList.ArticleListFragment


class MainActivity : AppCompatActivity(), ArticleListFragment.ArticlesActions {

    private var currentFragment: BaseFragment = ArticleListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transitionTo(currentFragment)

        supportFragmentManager.addOnBackStackChangedListener {
            currentFragment = supportFragmentManager.findFragmentById(R.id.main_activity_content) as BaseFragment
            setToolBar()
        }
    }

    private fun transitionTo(to: BaseFragment) {
        currentFragment = to
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_content, currentFragment)
            .addToBackStack(null)
            .commit()
        setToolBar()
    }

    private fun setToolBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = ""
    }

    override fun onTapArticle(url: String) {
        transitionTo(WebViewFragment.newInstance(url))
    }
}