package com.example.qiita_clone_android.ui.userList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qiita_clone_android.data.repository.UserRepository
import com.example.qiita_clone_android.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {
    private val repository = UserRepository()

    private var _users = MutableLiveData<List<User>>(listOf())
    val users: LiveData<List<User>>
        get() = _users

    fun setUsers(users: List<User>?) {
        if (users.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                _users.postValue(repository.fetchUsers())
            }
        } else {
            _users.postValue(users)
        }
    }
}