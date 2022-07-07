package com.example.qiita_clone_android.ui.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.ui.BaseFragment

class ArticlesFragment : BaseFragment() {

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