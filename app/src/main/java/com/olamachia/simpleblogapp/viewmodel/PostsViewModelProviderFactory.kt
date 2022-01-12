package com.olamachia.simpleblogapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olamachia.simpleblogapp.repository.PostsRepository

class PostsViewModelProviderFactory(
    private val postsRepository: PostsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostsViewModel(postsRepository) as T
    }
}