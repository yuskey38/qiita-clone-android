package com.example.qiita_clone_android.ui.articleFavoriteList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiita_clone_android.data.local.dao.article.ArticleFavoriteDao
import com.example.qiita_clone_android.databinding.FragmentArticleListBinding
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.BaseFragment
import com.example.qiita_clone_android.ui.MainActivity

class ArticleFavoriteListFragment : BaseFragment(), ArticleFavoriteAdapter.RecyclerViewHolder.ItemClickListener {
    private lateinit var binding: FragmentArticleListBinding
    private val viewModel: ArticleFavoriteListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater)

        viewModel.loadArticles()

        initViews()
        setObservers()

        return binding.root
    }

    private fun initViews() {
        setRecyclerView()

        val activity = activity as? MainActivity ?: return
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setObservers() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            updateArticles(articles)
        }
    }

    private fun setRecyclerView() {
        val recyclerView = binding.articleList
        val adapter = ArticleFavoriteAdapter(
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
        val adapter = recyclerView.adapter as ArticleFavoriteAdapter
        adapter.updateArticles(articles)
    }

    override fun onItemClick(view: View, article: Article) {
        (activity as? MainActivity)?.onTapArticle(article)
    }
}