package com.example.qiita_clone_android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
    }

    private fun setWebView() {
        val webView = binding.webView
        val url = arguments?.getString(OPEN_URL) ?: ""
        webView.loadUrl(url)
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