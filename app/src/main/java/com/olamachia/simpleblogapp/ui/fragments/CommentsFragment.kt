package com.olamachia.simpleblogapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.olamachia.simpleblogapp.R
import com.olamachia.simpleblogapp.adapters.CommentsAdapter
import com.olamachia.simpleblogapp.databinding.FragmentCommentsBinding
import com.olamachia.simpleblogapp.models.CommentResponseItem
import com.olamachia.simpleblogapp.models.ResponseItem
import com.olamachia.simpleblogapp.ui.MainActivity
import com.olamachia.simpleblogapp.utils.Constants.Companion.EMAIL
import com.olamachia.simpleblogapp.utils.Constants.Companion.NAME
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
        setPostButtonOnClickListener()

        binding.tvPostBody.text = postResponseItem.body

        commentsViewModel = (activity as MainActivity).postsViewModel
        commentsViewModel.getComments(postResponseItem.id)

        populateRecyclerView()
        observeNewComment(view)
    }

    //populate the recyclerview adapter with data to be displayed on the recyclerview
    private fun populateRecyclerView() {
        commentsViewModel.allComments.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    response.data?.let { commentResponse ->
                        commentsAdapter.differ.submitList(commentResponse)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    response.message?.let { errorMessage ->
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    //set observer on the viewmodel and alert user of the add comment operation
    private fun observeNewComment(view: View) {
        commentsViewModel.sentComments.observe(viewLifecycleOwner, { response->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { comment ->
//                        Log.d("TAG", "Posts: $comment")
                        Snackbar.make(view, "Comment added successfully", Snackbar.LENGTH_LONG).show()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
//                        Log.d("TAG", "Fetch error:$message")
                        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> { }
            }
        })
    }

    private fun setPostButtonOnClickListener() {
        binding.buttonPost.setOnClickListener {
            val comment = binding.etComment
            val commentText = comment.text.toString()
            val commentContent = CommentResponseItem(commentText, EMAIL, 501, NAME, postResponseItem.id)
            commentsViewModel.addNewComment(commentContent)
            comment.setText(R.string.empty_string)
        }
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
        commentsViewModel.allComments.value = null
    }
}