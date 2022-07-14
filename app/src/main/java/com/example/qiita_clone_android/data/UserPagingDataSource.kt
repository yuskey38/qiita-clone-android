package com.example.qiita_clone_android.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.qiita_clone_android.data.repository.UserRepository
import com.example.qiita_clone_android.models.User

class UserPagingDataSource(repository: UserRepository) : PagingSource<Int, User>() {
    companion object {
        const val PER_PAGE = 20
    }


    private val repository: UserRepository

    init {
        this.repository = repository
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val currentKey = params.key ?: 1
        return runCatching {
            repository.fetchUsers(currentKey, params.loadSize)
        }.fold(
            onSuccess = { users ->
                LoadResult.Page(
                    data = users,
                    prevKey = (currentKey - 1).takeIf { key -> key > 0 },
                    nextKey = (currentKey + 1).takeIf { users.size == params.loadSize }
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}