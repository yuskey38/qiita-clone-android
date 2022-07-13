package com.example.qiita_clone_android.ui.userList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.databinding.FragmentArticleListBinding
import com.example.qiita_clone_android.databinding.FragmentUserListBinding
import com.example.qiita_clone_android.ui.BaseFragment
import com.example.qiita_clone_android.ui.adapters.ArticleAdapter
import com.example.qiita_clone_android.ui.articleList.ArticleListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListFragment : BaseFragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    private lateinit var binding: FragmentUserListBinding
    private val viewModel: UserListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater)

        initViews()

        return binding.root
    }

    private fun initViews() {
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val usersAdapter = UserPagingAdapter()
        binding.userList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            viewModel.users.collectLatest { data ->
                usersAdapter.submitData(data)
            }
        }
    }
}