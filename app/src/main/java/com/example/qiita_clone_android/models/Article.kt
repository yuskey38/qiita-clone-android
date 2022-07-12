package com.example.qiita_clone_android.models

import java.io.Serializable

data class Article(
    val id: String,
    val title: String,
    val url: String,
    val user: QiitaUser
) : Serializable
