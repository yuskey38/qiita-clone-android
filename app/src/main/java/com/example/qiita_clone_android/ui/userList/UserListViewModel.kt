package com.example.qiita_clone_android.ui.userList

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.qiita_clone_android.data.UserPagingDataSource
import com.example.qiita_clone_android.data.repository.UserRepository
import com.example.qiita_clone_android.models.User
import com.example.qiita_clone_android.ui.articleList.ArticleListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val users =
        Pager(config = PagingConfig(
                initialLoadSize = UserPagingDataSource.PER_PAGE,
                pageSize = UserPagingDataSource.PER_PAGE),
            pagingSourceFactory = { UserPagingDataSource(repository) }
        ).flow.cachedIn(viewModelScope)

    class Factory(
        private val repository: UserRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserListViewModel(repository) as T
        }
    }
}