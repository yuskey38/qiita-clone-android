package com.example.qiita_clone_android.ui.articleFavoriteList

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiita_clone_android.databinding.FragmentArticleListBinding
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.BaseFragment
import com.example.qiita_clone_android.ui.MainActivity
import com.example.qiita_clone_android.ui.adapters.ArticleAdapter

class ArticleFavoriteListFragment : BaseFragment() {
    private val binding by lazy { FragmentArticleListBinding.inflate(layoutInflater) }
    private val viewModel: ArticleFavoriteListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.loadArticles()

        initViews()
        setObservers()

        return binding.root
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