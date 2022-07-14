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

    private lateinit var webView: WebView
    private lateinit var favoriteMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val article = arguments?.getSerializable(SELECTED_ARTICLE) as? Article?
        viewModel.fragmentIsReadyWith(article)
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

    private fun initViews() {
        setWebView()
        setOptionsMenu()
    }

    private fun setObservers() {
        viewModel.favoriteIconColor.observe(viewLifecycleOwner) {
            val drawableWrap = DrawableCompat.wrap(favoriteMenuItem.icon).mutate()
            DrawableCompat.setTint(drawableWrap, ResourcesCompat.getColor(resources, it, null))
            favoriteMenuItem.icon = drawableWrap
            favoriteMenuItem.isVisible = viewModel.showFavoriteIcon.value!!
        }

        viewModel.showFavoriteIcon.observe(viewLifecycleOwner) {
            activity?.invalidateOptionsMenu()
        }
    }

    private fun setWebView() {
        val url = arguments?.getString(OPEN_URL) ?: ""

        webView = binding.webView
        webView.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    viewModel.shouldOverrideUrlLoading(view?.originalUrl.toString(), request?.url.toString())
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
                favoriteMenuItem = menu.findItem(R.id.action_favorite)
                viewModel.onCreateMenu()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> parentFragmentManager.popBackStack()
                    R.id.action_share -> onTapShare()
                    R.id.action_favorite -> viewModel.onTapFavorite()
                }
                return false
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                viewModel.onPrepareMenu()
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onTapShare() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"

            putExtra(Intent.EXTRA_TEXT, webView.url)
            putExtra(Intent.EXTRA_TITLE, webView.title)
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
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