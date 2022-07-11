package com.example.qiita_clone_android.ui.articleFavoriteList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.models.Article
import com.squareup.picasso.Picasso

class ArticleFavoriteAdapter(
    private val context: Context,
    private val itemClickListener: RecyclerViewHolder.ItemClickListener,
    private val articles: List<Article>
) : RecyclerView.Adapter<ArticleFavoriteAdapter.RecyclerViewHolder>() {

    private var mRecyclerView: RecyclerView? = null
    private var _articles: List<Article> = articles

    fun updateArticles(newArticles: List<Article>) {
        _articles = newArticles
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null
    }

    override fun getItemCount(): Int {
        return _articles.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.let {
            it.titleText.text = _articles[position].title
            it.userIdText.text = _articles[position].user.id
            Picasso.get().load(_articles[position].user.profileImageUrl).fit().centerCrop().into(it.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.article_list_item, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, _articles[it.getChildAdapterPosition(view)])
            }
        }

        return RecyclerViewHolder(mView)
    }

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        interface ItemClickListener {
            fun onItemClick(view: View, article: Article)
        }

        val imageView: ImageView = view.findViewById(R.id.article_user_image)
        val titleText: TextView = view.findViewById(R.id.article_title)
        val userIdText: TextView = view.findViewById(R.id.article_user_id)

        init {
            // layoutの初期設定するときはココ
        }
    }

}