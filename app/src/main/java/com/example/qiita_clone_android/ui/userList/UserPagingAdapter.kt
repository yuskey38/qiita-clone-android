package com.example.qiita_clone_android.ui.userList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.qiita_clone_android.databinding.UserListItemBinding
import com.example.qiita_clone_android.models.User

class UserPagingAdapter :
    PagingDataAdapter<User, UserPagingAdapter.UsersViewHolder>(UsersComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersViewHolder {
        return UsersViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindUser(it) }
    }

    inner class UsersViewHolder(private val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindUser(item: User) = with(binding) {
            userId.text = item.id
        }
    }

    object UsersComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}