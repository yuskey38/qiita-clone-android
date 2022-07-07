package com.example.qiita_clone_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.ui.articles.ArticlesFragment

class MainActivity : AppCompatActivity(), ArticlesFragment.ArticlesActions {

    private var currentFragment: BaseFragment = ArticlesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_content, currentFragment).commit()

        supportFragmentManager.addOnBackStackChangedListener {
            currentFragment = supportFragmentManager.findFragmentById(R.id.main_activity_content) as BaseFragment
        }
    }

    override fun getArticles() {}

    override fun onTapArticle() {}
}