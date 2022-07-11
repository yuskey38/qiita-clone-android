package com.example.qiita_clone_android.data.local.entity

import com.example.qiita_clone_android.models.Article
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class ArticleFavoriteEntity(
    @PrimaryKey var _id: String = "",
    var title: String = "",
    var url: String = "",
    var userId: String = "",
    var profileImage: String = ""
) : RealmObject {

    // これがないとエラーになる
    // [Realm] Cannot find primary zero arg constructor
    constructor() : this("", "", "", "", "")

    companion object {
        fun create(
            article: Article
        ): ArticleFavoriteEntity {
            return ArticleFavoriteEntity(
                article.id,
                article.title,
                article.url,
                article.user.id,
                article.user.profileImageUrl
            )
        }
    }
}