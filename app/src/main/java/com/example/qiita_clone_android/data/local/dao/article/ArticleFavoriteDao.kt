package com.example.qiita_clone_android.data.local.dao.article

import com.example.qiita_clone_android.data.local.entity.ArticleFavoriteEntity
import com.example.qiita_clone_android.models.Article
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

class ArticleFavoriteDao {
    private val config = RealmConfiguration.Builder(schema = setOf(ArticleFavoriteEntity::class))
        .deleteRealmIfMigrationNeeded()
        .build()

    fun findBy(id: String): Article? {
        val realm: Realm = Realm.open(config)
        return realm.query<ArticleFavoriteEntity>("id == $0", id).find().firstOrNull()?.toArticle()
    }

    fun findAll(): List<Article> {
        val realm: Realm = Realm.open(config)
        return realm.query<ArticleFavoriteEntity>().find().map { it.toArticle() }
    }

    fun insert(article: Article) {
        val realm: Realm = Realm.open(config)
        realm.writeBlocking {
            copyToRealm(ArticleFavoriteEntity.create(article))
        }
    }

    fun delete(article: Article) {
        val realm: Realm = Realm.open(config)
        realm.writeBlocking {
            val articleEntity =
                this.query<ArticleFavoriteEntity>("id == $0", article.id).find().firstOrNull()

            articleEntity?.let {
                delete(it)
            }
        }
    }

    fun clear() {
        val realm: Realm = Realm.open(config)
        realm.writeBlocking {
            val articles = this.query<ArticleFavoriteEntity>().find()
            delete(articles)
        }
    }
}