package com.adilashraf.retrofitandvolley.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.retrofitandvolley.databinding.CommentLayoutBinding
import com.adilashraf.retrofitandvolley.model.CommentsItem

class CommentAdapter(val commentList: ArrayList<CommentsItem>, val context: Context):
RecyclerView.Adapter<CommentAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = CommentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(vh)
     }

    override fun getItemCount(): Int  = commentList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = commentList[position]

        holder.binding.apply {
            id.text = pos.id.toString()
            email.text = pos.email
            name.text = "Your have a message from ${pos.name}"
            comment.text = pos.body
        }

     }

    class ViewHolder(val binding: CommentLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    }

}