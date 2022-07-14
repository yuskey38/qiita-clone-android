package com.example.qiita_clone_android.ui.userList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qiita_clone_android.databinding.FragmentUserListBinding
import com.example.qiita_clone_android.ui.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListFragment : BaseFragment() {
    private val binding by lazy { FragmentUserListBinding.inflate(layoutInflater) }
    private val viewModel: UserListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViews()

        return binding.root
    }

    private fun initViews() {
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val usersAdapter = UserPagingAdapter()
        binding.userList.apply {
            layoutManager = GridLayoutManager(
                context, 2, RecyclerView.VERTICAL, false
            )
            adapter = usersAdapter
        }

        lifecycleScope.launch {
            viewModel.users.collectLatest { data ->
                usersAdapter.submitData(data)
            }
        }
    }
}