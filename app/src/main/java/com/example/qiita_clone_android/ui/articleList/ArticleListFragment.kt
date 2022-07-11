package com.example.qiita_clone_android.ui.articleList

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.databinding.FragmentArticleListBinding
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.BaseFragment
import com.example.qiita_clone_android.ui.MainActivity

class ArticleListFragment : BaseFragment(), ArticleAdapter.RecyclerViewHolder.ItemClickListener {
    private lateinit var binding: FragmentArticleListBinding
    private val viewModel: ArticleListViewModel by viewModels()

    interface ArticlesActions {
        fun onTapArticle(article: Article)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMenuVisibility(true)
        viewModel.fetchArticles()
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

    private fun initViews() {
        setOptionsMenu()
        setRecyclerView()
    }

    private fun setObservers() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            updateArticles(articles)
        }
    }

    private fun setOptionsMenu() {
        activity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.article_list_menu, menu)
                val searchView: SearchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String): Boolean {
                        viewModel.updateQuery(newText)
                        return false
                    }
                    override fun onQueryTextSubmit(query: String): Boolean {
                        viewModel.fetchArticles()
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
            listOf()
        )
        val layoutManager =
            LinearLayoutManager(
                binding.root.context, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    private fun setPullToRefresh() {
        val refresh = binding.refreshContainer
        refresh.setOnRefreshListener {
            viewModel.fetchArticles()
            refresh.isRefreshing = false
        }
    }

    private fun updateArticles(articles: List<Article>) {
        val recyclerView = binding.articleList
        val adapter = recyclerView.adapter as ArticleAdapter
        adapter.updateArticles(articles)
    }

    override fun onItemClick(view: View, article: Article) {
        (activity as? MainActivity)?.onTapArticle(article)
    }
}