package com.example.qiita_clone_android.ui.userList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.qiita_clone_android.databinding.UserListItemBinding
import com.example.qiita_clone_android.models.User
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class UserPagingAdapter :
    PagingDataAdapter<User, UserPagingAdapter.ViewHolder>(Comparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            binding.apply {
                userId.text = "@${item.id}"
                githubAccount.text = "GitHub: ${unwrappedText(item.githubLoginName)}"
                twitterAccount.text = "Twitter: ${unwrappedText(item.twitterScreenName)}"
                facebookAccount.text = "Facebook: ${unwrappedText(item.facebookId)}"
                linkedInAccount.text = "LinkedIn: ${unwrappedText(item.linkedinId)}"
            }.let {
                Picasso.get().load(item.profileImageUrl)
                    .transform(CropCircleTransformation())
                    .fit()
                    .into(it.profileImage)
            }
        }

        private fun unwrappedText(text: String?): String {
            return if (text.isNullOrEmpty()) "None" else text
        }
    }

    object Comparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}