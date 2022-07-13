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
            val currentKey = params.key ?: 1
            val users = repository.fetchUsers(currentKey, params.loadSize)

            LoadResult.Page(
                data = users,
                prevKey = (currentKey - 1).takeIf { key -> key > 0 },
                nextKey = (currentKey + 1).takeIf { users.size == params.loadSize }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}