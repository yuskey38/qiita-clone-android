package com.example.qiita_clone_android.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val id: String = "",
    @SerializedName("profile_image_url")
    val profileImageUrl: String = "",
    @SerializedName("github_login_name")
    val githubLoginName: String = "",
    @SerializedName("twitter_screen_name")
    val twitterScreenName: String = "",
    @SerializedName("facebook_id")
    val facebookId: String = "",
    @SerializedName("linkedin_id")
    val linkedinId: String = "",
) : Serializable