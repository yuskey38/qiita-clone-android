package com.example.qiita_clone_android.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val description: String = "",
    @SerializedName("facebook_id")
    val facebookId: String = "",
    @SerializedName("followees_count")
    val followeesCount: Int = 0,
    @SerializedName("followers_count")
    val followersCount: Int = 0,
    @SerializedName("github_login_name")
    val githubLoginName: String = "",
    val id: String = "",
    @SerializedName("items_count")
    val itemsCount: Int = 0,
    @SerializedName("linkedin_id")
    val linkedinId: String = "",
    val location: String = "",
    val name: String = "",
    val organization: String = "",
    @SerializedName("permanent_id")
    val permanentId: Int = 0,
    @SerializedName("profile_image_url")
    val profileImageUrl: String = "",
    @SerializedName("team_only")
    val teamOnly: Boolean = false,
    @SerializedName("twitter_screen_name")
    val twitterScreenName: String = "",
    @SerializedName("website_url")
    val websiteUrl: String = ""
) : Serializable