package com.example.qiita_clone_android.ui.webView

import androidx.lifecycle.ViewModel
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.data.repository.ArticleRepository
import com.example.qiita_clone_android.models.Article

class WebViewModel : ViewModel() {

    private val articleRepository = ArticleRepository()

    var selectedArticle: Article? = null
        private set

    var showFavoriteIcon: Boolean = true
        private set

    fun setSelectedArticle(article: Article?) {
        selectedArticle = article
    }

    fun setShowFavoriteIcon(isShow: Boolean) {
        showFavoriteIcon = isShow
    }

    fun switchFavorite() {
        val article = selectedArticle ?: return
        val favorite = articleRepository.getFavoriteBy(article.id)
        if (favorite == null) {
            articleRepository.addFavorite(article)
        } else {
            articleRepository.removeFavorite(article)
        }
    }

    fun getFavoriteIconColor(): Int {
        val article = selectedArticle ?: return R.color.white
        val favorite = articleRepository.getFavoriteBy(article.id)
        return if (favorite == null) R.color.white else R.color.yellow
    }
}