package com.olamachia.simpleblogapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.olamachia.simpleblogapp.R
import com.olamachia.simpleblogapp.models.CommentResponseItem

class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val commenterNameTextView: TextView = itemView.findViewById(R.id.tv_commenter_name)
        val commenterEmailTextView: TextView = itemView.findViewById(R.id.tv_commenter_email)
        val commentBodyTextView: TextView = itemView.findViewById(R.id.tv_comment_body)
    }

    val differCallBack = object:DiffUtil.ItemCallback<CommentResponseItem>() {
        override fun areItemsTheSame(oldItem: CommentResponseItem, newItem: CommentResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CommentResponseItem, newItem: CommentResponseItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    var differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commenterName = holder.commenterNameTextView
        val commenterEmail = holder.commenterEmailTextView
        val commentBody = holder.commentBodyTextView

        commenterEmail.text = differ.currentList[position].email
        commenterName.text = differ.currentList[position].name
        commentBody.text = differ.currentList[position].body
    }

    override fun getItemCount() = differ.currentList.size
}