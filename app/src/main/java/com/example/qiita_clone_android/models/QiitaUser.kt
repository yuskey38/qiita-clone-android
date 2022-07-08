package com.example.qiita_clone_android.models

import com.google.gson.annotations.SerializedName

data class QiitaUser(
    val id: String,
    @SerializedName("profile_image_url")
    val profileImageUrl: String
)
