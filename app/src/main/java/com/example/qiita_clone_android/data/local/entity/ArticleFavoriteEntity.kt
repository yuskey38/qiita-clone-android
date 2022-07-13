package com.example.qiita_clone_android.data.local.entity

import com.example.qiita_clone_android.models.Article
import com.example.qiita_clone_android.models.User
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class ArticleFavoriteEntity : RealmObject {
    @PrimaryKey
    var id: String = ""
    var title: String = ""
    var url: String = ""
    var userId: String = ""
    var profileImage: String = ""

    fun toArticle(): Article {
        return Article(id, title, url, User(id = userId, profileImageUrl = profileImage))
    }

    companion object {
        fun create(
            article: Article
        ): ArticleFavoriteEntity {
            return ArticleFavoriteEntity().also {
                it.id = article.id
                it.title = article.title
                it.url = article.url
                it.userId = article.user.id
                it.profileImage = article.user.profileImageUrl
            }
        }
    }
}