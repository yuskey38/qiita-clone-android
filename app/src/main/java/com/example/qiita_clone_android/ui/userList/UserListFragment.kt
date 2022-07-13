package com.example.qiita_clone_android.ui.userList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiita_clone_android.R
import com.example.qiita_clone_android.databinding.FragmentArticleListBinding
import com.example.qiita_clone_android.databinding.FragmentUserListBinding
import com.example.qiita_clone_android.ui.BaseFragment
import com.example.qiita_clone_android.ui.adapters.ArticleAdapter
import com.example.qiita_clone_android.ui.articleList.ArticleListViewModel

class UserListFragment : BaseFragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    private lateinit var binding: FragmentUserListBinding
    private val viewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setUsers(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater)

        initViews()
        setObservers()

        return binding.root
    }

    private fun initViews() {
        setRecyclerView()
    }

    private fun setObservers() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            users.forEach {
                Log.v("USERS", it.id)
            }
        }
    }

    private fun setRecyclerView() {
//        val recyclerView = binding.userList
//        val adapter = ArticleAdapter(
//            binding.root.context,
//            this,
//        )
//        val layoutManager =
//            LinearLayoutManager(
//                binding.root.context, LinearLayoutManager.VERTICAL, false
//            )
//
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = layoutManager
    }
}