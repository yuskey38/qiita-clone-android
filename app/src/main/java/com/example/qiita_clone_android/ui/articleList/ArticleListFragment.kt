package com.example.qiita_clone_android.ui.articleList

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.databinding.FragmentArticleListBinding
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.BaseFragment
import com.example.qiita_clone_android.ui.MainActivity
import com.example.qiita_clone_android.ui.adapters.ArticleAdapter

class ArticleListFragment : BaseFragment(), ArticleAdapter.RecyclerViewHolder.ItemClickListener {
    private lateinit var binding: FragmentArticleListBinding
    private val viewModel: ArticleListViewModel by viewModels()

    interface ArticlesActions {
        fun onTapArticle(article: Article)
        fun saveArticles(articles: List<Article>)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val articles = (activity as? MainActivity)?.viewModel?.articles
        viewModel.setArticles(articles)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater)

        initViews()
        setObservers()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.saveArticles(viewModel.articles.value ?: emptyList())
    }

    private fun initViews() {
        binding.loading.visibility = VISIBLE
        setOptionsMenu()
        setRecyclerView()
    }

    private fun setObservers() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            updateArticles(articles)
        }
    }

    private fun setOptionsMenu() {
        val activity = activity as? MainActivity ?: return
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        activity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.article_list_menu, menu)
                val searchView: SearchView =
                    menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String): Boolean {
                        viewModel.updateQuery(newText)
                        return false
                    }

                    override fun onQueryTextSubmit(query: String): Boolean {
                        binding.loading.visibility = VISIBLE
                        viewModel.setArticles(null)
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setRecyclerView() {
        setPullToRefresh()

        val recyclerView = binding.articleList
        val adapter = ArticleAdapter(
            binding.root.context,
            this,
        )
        val layoutManager =
            LinearLayoutManager(
                binding.root.context, LinearLayoutManager.VERTICAL, false
            )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    private fun setPullToRefresh() {
        binding.refreshContainer.setOnRefreshListener {
            viewModel.setArticles(null)
        }
    }

    private fun updateArticles(articles: List<Article>) {
        binding.refreshContainer.isRefreshing = false
        binding.loading.visibility = GONE

        val recyclerView = binding.articleList
        val adapter = recyclerView.adapter as ArticleAdapter
        adapter.updateArticles(articles)
    }

    override fun onItemClick(view: View, article: Article) {
        (activity as? MainActivity)?.onTapArticle(article)
    }
}