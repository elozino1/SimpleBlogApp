package com.olamachia.simpleblogapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.olamachia.simpleblogapp.R
import com.olamachia.simpleblogapp.adapters.PostsAdapter
import com.olamachia.simpleblogapp.databinding.FragmentPostsBinding
import com.olamachia.simpleblogapp.ui.MainActivity
import com.olamachia.simpleblogapp.utils.Resource
import com.olamachia.simpleblogapp.viewmodel.PostsViewModel

class PostsFragment : Fragment() {

    private lateinit var postsViewModel: PostsViewModel
    lateinit var postsAdapter: PostsAdapter

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPostsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsViewModel = (activity as MainActivity).postsViewModel
        setupRecyclerView()
        setSearchListener()
        setFABOnClickListener()
        observeResultsFromAllPostsFetch()
    }

    //set observer on the viewmodel livedata and pass list of post to PostsAdapter
    private fun observeResultsFromAllPostsFetch() {
        postsViewModel.allPosts.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    response.data?.let { postsResponse ->
                        postsAdapter.differ.submitList(postsResponse)
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

    private fun setFABOnClickListener() {
        binding.fab.setOnClickListener {
            val direction = PostsFragmentDirections.actionPostsFragmentToAddPostFragment()
            it.findNavController().navigate(direction)
        }
    }

    private fun setSearchListener() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupRecyclerView() {
        postsAdapter = PostsAdapter()
        val postsRecyclerView = binding.postsRecyclerView
        postsRecyclerView.adapter = postsAdapter
        postsRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}