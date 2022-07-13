package com.example.qiita_clone_android.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.qiita_clone_android.data.repository.UserRepository
import com.example.qiita_clone_android.models.User

class UserPagingDataSource(repository: UserRepository) : PagingSource<Int, User>() {
    private val repository: UserRepository

    init {
        this.repository = repository
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val nextPageNumber = params.key ?: 1
            val users = repository.fetchUsers(nextPageNumber, params.loadSize)

            LoadResult.Page(
                data = users,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (users.size == params.loadSize) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int {
        return 1
    }
}