package com.olamachia.simpleblogapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.olamachia.simpleblogapp.adapters.CommentsAdapter
import com.olamachia.simpleblogapp.databinding.FragmentCommentsBinding
import com.olamachia.simpleblogapp.models.ResponseItem
import com.olamachia.simpleblogapp.ui.MainActivity
import com.olamachia.simpleblogapp.utils.Resource
import com.olamachia.simpleblogapp.viewmodel.PostsViewModel

class CommentsFragment : Fragment() {

    private val args: CommentsFragmentArgs by navArgs()
    private lateinit var postResponseItem: ResponseItem

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var commentsViewModel: PostsViewModel

    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCommentsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postResponseItem = args.post

        setupRecyclerView()
        commentsViewModel = (activity as MainActivity).postsViewModel
        commentsViewModel.getComments(postResponseItem.id)
        commentsViewModel.allComments.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { commentResponse ->
                        commentsAdapter.differ.submitList(commentResponse)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { errorMessage ->
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    Toast.makeText(activity, "Loading Comments", Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.tvPostBody.text = postResponseItem.body
    }

    private fun setupRecyclerView() {
        commentsAdapter = CommentsAdapter()
        val commentsRecyclerView = binding.commentsRecyclerview
        commentsRecyclerView.adapter = commentsAdapter
        commentsRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}