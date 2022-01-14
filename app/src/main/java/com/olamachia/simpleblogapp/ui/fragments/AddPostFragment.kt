package com.olamachia.simpleblogapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.olamachia.simpleblogapp.R
import com.olamachia.simpleblogapp.databinding.FragmentAddPostBinding
import com.olamachia.simpleblogapp.models.ResponseItem
import com.olamachia.simpleblogapp.ui.MainActivity
import com.olamachia.simpleblogapp.utils.Resource
import com.olamachia.simpleblogapp.viewmodel.PostsViewModel

class AddPostFragment : Fragment() {

    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).postsViewModel

        setPostButtonOnClickListener()
        observeNewPost(view)
    }

    //set observer on the viewmodel and alert user of the add post operation
    private fun observeNewPost(view: View) {
        viewModel.sentPosts.observe(viewLifecycleOwner, { response->
            when(response) {
                is Resource.Success -> {
                    response.data?.let {
//                        Log.d("Send Post Fragment", "Posts: $post")
                        Snackbar.make(view, "Post added successfully", Snackbar.LENGTH_LONG).show()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
//                        Log.d("Send Post Fragment", "Fetch error:$message")
                        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> { }
            }
        })
    }

    private fun setPostButtonOnClickListener() {
        binding.buttonPost.setOnClickListener {
            val postTitle = binding.etPostTitle
            val postBody = binding.etPostBody
            val postTitleText = postTitle.text.toString()
            val postBodyText = postBody.text.toString()
            val postContent = ResponseItem(postBodyText, 1, postTitleText, 1)
            viewModel.addNewPost(postContent)
            postTitle.setText(R.string.empty_string)
            postBody.setText(R.string.empty_string)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}