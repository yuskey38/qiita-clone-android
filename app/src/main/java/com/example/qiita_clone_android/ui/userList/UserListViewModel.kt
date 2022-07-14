package com.example.qiita_clone_android.ui.userList

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.qiita_clone_android.data.UserPagingDataSource
import com.example.qiita_clone_android.data.repository.UserRepository
import com.example.qiita_clone_android.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {
    private val repository = UserRepository()

    val users =
        Pager(config = PagingConfig(
                initialLoadSize = UserPagingDataSource.PER_PAGE,
                pageSize = UserPagingDataSource.PER_PAGE),
            pagingSourceFactory = { UserPagingDataSource(repository) }
        ).flow.cachedIn(viewModelScope)
}