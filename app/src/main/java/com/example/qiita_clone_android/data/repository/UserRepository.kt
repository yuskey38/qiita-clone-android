package com.example.qiita_clone_android.data.repository

import com.example.qiita_clone_android.data.remote.ApiClient
import com.example.qiita_clone_android.data.remote.ApiRequest
import com.example.qiita_clone_android.models.User

class UserRepository {
    fun fetchUsers(): List<User> {
        return ApiClient.retrofit.create(ApiRequest::class.java)
            .fetchUsers(1, 20)
            .execute()
            .body() ?: listOf()
    }
}