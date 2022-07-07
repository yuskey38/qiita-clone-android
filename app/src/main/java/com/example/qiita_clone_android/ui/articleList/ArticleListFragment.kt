package com.example.qiita_clone_android.ui.articleList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qiita_clone_android.ui.BaseFragment

class ArticleListFragment : BaseFragment() {

    interface ArticlesActions {
        fun getArticles()
        fun onTapArticle()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return null
    }

}