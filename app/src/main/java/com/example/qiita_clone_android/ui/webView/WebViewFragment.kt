package com.example.qiita_clone_android.ui.webView

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.databinding.FragmentWebViewBinding
import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.ui.BaseFragment
import com.example.qiita_clone_android.ui.MainActivity

class WebViewFragment : BaseFragment() {
    private val binding by lazy { FragmentWebViewBinding.inflate(layoutInflater) }
    private val viewModel: WebViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViews()
        initViewModel()

        return binding.root
    }

    private fun initViews() {
        setWebView()
        setOptionsMenu()
    }

    private fun initViewModel() {
        val article = arguments?.getSerializable(SELECTED_ARTICLE) as? Article?
        viewModel.setSelectedArticle(article)
    }

    private fun setWebView() {
        val url = arguments?.getString(OPEN_URL) ?: ""

        binding.webView.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    viewModel.setShowFavoriteIcon(view?.originalUrl == request?.url.toString())
                    activity?.invalidateOptionsMenu()
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
        }.loadUrl(url)
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
                    R.id.action_share -> onTapShare()
                    R.id.action_favorite -> onTapFavorite(menuItem)
                }
                return false
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                val favorite = menu.findItem(R.id.action_favorite)
                setFavoriteIcon(favorite)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onTapShare() {
        val webView = binding.webView
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"

            putExtra(Intent.EXTRA_TEXT, webView.url)
            putExtra(Intent.EXTRA_TITLE, webView.title)
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun onTapFavorite(item: MenuItem) {
        viewModel.switchFavorite()
        setFavoriteIcon(item)
    }

    private fun setFavoriteIcon(item: MenuItem) {
        val iconColor = viewModel.getFavoriteIconColor()
        val drawableWrap = DrawableCompat.wrap(item.icon).mutate()
        DrawableCompat.setTint(drawableWrap, ResourcesCompat.getColor(resources, iconColor, null))
        item.icon = drawableWrap
        item.isVisible = viewModel.showFavoriteIcon
    }

    companion object {
        const val OPEN_URL = "com.example.qiita_clone_android.ui.webView.WebViewFragment.OPEN_URL"
        const val SELECTED_ARTICLE =
            "com.example.qiita_clone_android.ui.webView.WebViewFragment.SELECTED_ARTICLE"

        fun newInstance(url: String, article: Article? = null): WebViewFragment {
            return WebViewFragment().apply {
                arguments = bundleOf(
                    OPEN_URL to url,
                    SELECTED_ARTICLE to article
                )
            }
        }
    }
}