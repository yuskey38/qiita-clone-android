package com.example.qiita_clone_android.ui.webView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.data.repository.ArticleRepository
import com.example.qiita_clone_android.models.Article

class WebViewModel : ViewModel() {

    private val articleRepository = ArticleRepository()

    var selectedArticle: Article? = null
        private set

    private var _showFavoriteIcon: MutableLiveData<Boolean> = MutableLiveData(true)
    val showFavoriteIcon: LiveData<Boolean>
        get() = _showFavoriteIcon

    private var _favoriteIconColor: MutableLiveData<Int> = MutableLiveData()
    val favoriteIconColor: LiveData<Int>
        get() = _favoriteIconColor

    fun fragmentIsReadyWith(article: Article?) {
        selectedArticle = article
    }

    fun onCreateMenu() {
        setFavoriteIconColor()
    }
    fun onPrepareMenu() {
        setFavoriteIconColor()
    }

    fun shouldOverrideUrlLoading(originalUrl: String, loadingUrl: String) {
        _showFavoriteIcon.value = originalUrl == loadingUrl
    }

    fun onTapFavorite() {
        val article = selectedArticle ?: return
        val favorite = articleRepository.getFavoriteBy(article.id)
        if (favorite == null) {
            articleRepository.addFavorite(article)
        } else {
            articleRepository.removeFavorite(article)
        }
        setFavoriteIconColor()
    }

    private fun setFavoriteIconColor() {
        val article = selectedArticle ?: return
        val favorite = articleRepository.getFavoriteBy(article.id)
        _favoriteIconColor.value = if (favorite == null) R.color.white else R.color.yellow
    }
}