package com.example.qiita_clone_android.ui.articleFavoriteList

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiita_clone_android.data.local.dao.article.ArticleFavoriteDao
import com.example.qiita_clone_android.data.repository.ArticleRepository
import com.example.qiita_clone_android.databinding.FragmentArticleListBinding
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.BaseFragment
import com.example.qiita_clone_android.ui.MainActivity
import com.example.qiita_clone_android.ui.adapters.ArticleAdapter
import com.example.qiita_clone_android.ui.articleList.ArticleListViewModel

class ArticleFavoriteListFragment : BaseFragment() {
    private val binding by lazy { FragmentArticleListBinding.inflate(layoutInflater) }
    private val viewModel: ArticleFavoriteListViewModel by lazy {
        val favoriteDao = ArticleFavoriteDao()
        val repository = ArticleRepository(favoriteDao)
        val factory = ArticleFavoriteListViewModel.Factory(repository)
        ViewModelProvider(this, factory)[ArticleFavoriteListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViews()
        setObservers()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    private fun initViews() {
        setRecyclerView()
        binding.loading.visibility = GONE
        binding.refreshContainer.isEnabled = false

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setObservers() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            updateArticles(articles)
        }
    }

    private fun setRecyclerView() {
        binding.articleList.apply {
            adapter = ArticleAdapter(object : ArticleAdapter.ItemClickListener {
                    override fun onItemClick(view: View, article: Article) {
                        (activity as? MainActivity)?.onTapArticle(article)
                    }
                }
            )
            layoutManager = LinearLayoutManager(
                binding.root.context, LinearLayoutManager.VERTICAL, false
            )
        }
    }

    private fun updateArticles(articles: List<Article>) {
        val adapter = binding.articleList.adapter as ArticleAdapter
        adapter.submitList(articles)
    }
}