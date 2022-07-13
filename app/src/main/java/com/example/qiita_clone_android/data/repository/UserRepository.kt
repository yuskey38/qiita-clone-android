package com.example.qiita_clone_android.data.repository

import com.example.qiita_clone_android.data.remote.ApiClient
import com.example.qiita_clone_android.data.remote.ApiRequest
import com.example.qiita_clone_android.models.User

class UserRepository {

    suspend fun fetchUsers(page: Int, perPage: Int): List<User> {
        return ApiClient.retrofit.create(ApiRequest::class.java)
            .fetchUsers(page, perPage)
    }
}