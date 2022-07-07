package com.example.qiita_clone_android.ui.articleList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.qiita_clone_android.databinding.FragmentArticleListBinding
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.BaseFragment

class ArticleListFragment : BaseFragment() {
    private lateinit var binding: FragmentArticleListBinding
    private val viewModel: ArticleListViewModel by viewModels()

    interface ArticlesActions {
        fun getArticles()
        fun onTapArticle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchArticles(1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater)

        initViews()

        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            updateArticles(articles)
        }

        return binding.root
    }

    private fun initViews() {
        setListView()
    }

    private fun setListView() {
        val listView = binding.articlesListView
    }

    private fun updateArticles(articles: List<Article>) {
        val adapter: ArrayAdapter<Article> = ArrayAdapter(
            binding.root.context,
            android.R.layout.simple_list_item_1,
            articles
        )
        binding.articlesListView.adapter = adapter
    }

}