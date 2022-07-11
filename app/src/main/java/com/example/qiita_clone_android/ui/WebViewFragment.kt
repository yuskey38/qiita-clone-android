package com.example.qiita_clone_android.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.databinding.FragmentWebViewBinding
import com.example.qiita_clone_android.models.Article

class WebViewFragment : BaseFragment() {

    private lateinit var binding: FragmentWebViewBinding

    interface WebViewActions {
        fun onTapFavorite()
    }

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
        val url = arguments?.getString(OPEN_URL) ?: ""
        webView.loadUrl(url)
    }

    private fun setOptionsMenu() {
        val activity = activity as? MainActivity ?: return
        activity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.webview_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.action_favorite -> activity.onTapFavorite()
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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