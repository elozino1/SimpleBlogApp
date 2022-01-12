package com.olamachia.simpleblogapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.olamachia.simpleblogapp.R
import com.olamachia.simpleblogapp.models.ResponseItem
import com.olamachia.simpleblogapp.ui.fragments.PostsFragmentDirections

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View, var post: ResponseItem? = null) : RecyclerView.ViewHolder(itemView) {

        val postsTitleTextView: TextView = itemView.findViewById(R.id.tv_post_title)
        val postsBodyTextView: TextView = itemView.findViewById(R.id.tv_post_body)

        init {
            itemView.setOnClickListener {
                post?.let {
                    val direction = PostsFragmentDirections.actionPostsFragmentToCommentsFragment(it)
                    itemView.findNavController().navigate(direction)
                }
            }
        }
    }

    private val differCallBack = object: DiffUtil.ItemCallback<ResponseItem>() {
        override fun areItemsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postsTitle = holder.postsTitleTextView
        val postsBody = holder.postsBodyTextView

        postsTitle.text = differ.currentList[position].title
        postsBody.text = differ.currentList[position].body

        holder.post = differ.currentList[position]
    }

    override fun getItemCount() = differ.currentList.size
}
