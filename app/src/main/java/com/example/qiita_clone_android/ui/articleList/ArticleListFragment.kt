package com.example.qiita_clone_android.ui.articleList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiita_clone_android.databinding.FragmentArticleListBinding
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.BaseFragment
import com.example.qiita_clone_android.ui.MainActivity

class ArticleListFragment : BaseFragment(), ArticleAdapter.RecyclerViewHolder.ItemClickListener {
    private lateinit var binding: FragmentArticleListBinding
    private val viewModel: ArticleListViewModel by viewModels()

    interface ArticlesActions {
        fun onTapArticle(url: String)
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
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val recyclerView = binding.articleList
        val adapter = ArticleAdapter(
            binding.root.context,
            this,
            listOf()
        )
        val layoutManager =
            LinearLayoutManager(
                binding.root.context, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    private fun updateArticles(articles: List<Article>) {
        val recyclerView = binding.articleList
        val adapter = recyclerView.adapter as ArticleAdapter
        adapter.updateArticles(articles)
    }

    override fun onItemClick(view: View, article: Article) {
        (activity as? MainActivity)?.onTapArticle(article.url)
    }
}