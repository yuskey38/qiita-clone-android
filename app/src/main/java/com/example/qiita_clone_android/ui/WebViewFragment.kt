package com.example.qiita_clone_android.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.data.local.dao.article.ArticleFavoriteDao
import com.example.qiita_clone_android.databinding.FragmentWebViewBinding


class WebViewFragment : BaseFragment() {

    private lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater)

        initViews()

        return binding.root
    }

    private fun initViews() {
        setWebView()
        setOptionsMenu()
    }

    private fun setWebView() {
        val webView = binding.webView
        webView.webViewClient = WebViewClient()

        val url = arguments?.getString(OPEN_URL) ?: ""
        webView.loadUrl(url)
    }

    private fun setOptionsMenu() {
        val activity = activity as? MainActivity ?: return
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.webview_menu, menu)

                setFavoriteIcon(menu.findItem(R.id.action_favorite))
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> parentFragmentManager.popBackStack()
                    R.id.action_favorite -> onTapFavorite(menuItem)
                    R.id.action_share -> onTapShare()
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onTapFavorite(item: MenuItem) {
        val activity = activity as? MainActivity ?: return
        activity.viewModel.selectedArticle?.let { article ->
            val dao = ArticleFavoriteDao()
            val favorite = dao.findBy(article.id)

            if (favorite == null) {
                dao.insert(article)
            } else {
                dao.delete(article)
            }

            val fabIcon =
                if (favorite == null) android.R.drawable.star_on else android.R.drawable.star_off
            item.icon = ResourcesCompat.getDrawable(resources, fabIcon, null)
        }
    }

    private fun onTapShare() {
        val activity = activity as? MainActivity ?: return
        val url = arguments?.getString(OPEN_URL) ?: ""
        val article = activity.viewModel.selectedArticle
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"

            putExtra(Intent.EXTRA_TEXT, url)
            if (!article?.title.isNullOrEmpty()) {
                putExtra(Intent.EXTRA_TITLE, article!!.title)
            }
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }

    private fun setFavoriteIcon(item: MenuItem) {
        val activity = activity as? MainActivity ?: return
        activity.viewModel.selectedArticle?.let { article ->
            val dao = ArticleFavoriteDao()
            val favorite = dao.findBy(article.id)
            val fabIcon =
                if (favorite == null) android.R.drawable.star_off else android.R.drawable.star_on
            item.icon = ResourcesCompat.getDrawable(resources, fabIcon, null)
        }
    }

    companion object {
        const val OPEN_URL = "com.example.qiita_clone_android.ui.WebViewFragment.URL"
        fun newInstance(url: String): WebViewFragment {
            return WebViewFragment().apply {
                arguments = bundleOf(OPEN_URL to url)
            }
        }
    }
}