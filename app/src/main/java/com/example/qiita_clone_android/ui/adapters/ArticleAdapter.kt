package com.example.qiita_clone_android.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.qiita_clone_android.databinding.ArticleListItemBinding
import com.example.qiita_clone_android.models.Article
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class ArticleAdapter(
    private val itemClickListener: ItemClickListener
) : ListAdapter<Article, ArticleAdapter.ViewHolder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holderUser: ViewHolder, position: Int) {
        holderUser.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ArticleListItemBinding,
        private val itemClickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                articleTitle.text = article.title
                articleUserId.text = if (article.user.id.isEmpty()) "" else "@${article.user.id}"
            }.let { view ->
                view.root.setOnClickListener { itemClickListener.onItemClick(it, article) }

                Picasso.get().load(article.user.profileImageUrl)
                    .transform(CropCircleTransformation())
                    .fit()
                    .into(view.articleUserImage)
            }
        }
    }

    object Comparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemClickListener {
        fun onItemClick(view: View, article: Article)
    }
}