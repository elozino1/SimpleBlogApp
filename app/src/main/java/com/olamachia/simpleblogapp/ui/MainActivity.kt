package com.olamachia.simpleblogapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import com.olamachia.simpleblogapp.R
import com.olamachia.simpleblogapp.repository.PostsRepository
import com.olamachia.simpleblogapp.viewmodel.PostsViewModel
import com.olamachia.simpleblogapp.viewmodel.PostsViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    lateinit var postsViewModel: PostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postsRepository = PostsRepository()
        val postsViewModelProviderFactory = PostsViewModelProviderFactory(postsRepository)
        postsViewModel = ViewModelProvider(this, postsViewModelProviderFactory).get(PostsViewModel::class.java)
    }

}