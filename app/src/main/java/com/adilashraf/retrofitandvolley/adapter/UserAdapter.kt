package com.adilashraf.retrofitandvolley.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.retrofitandvolley.databinding.UserDataLayoutBinding
import com.adilashraf.retrofitandvolley.model.UsersItem
import com.bumptech.glide.Glide

class UserAdapter(private val userList: ArrayList<UsersItem>, val context: Context) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val vh = UserDataLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return UserViewHolder(vh)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val pos = userList[position]
        val uriSting = pos.avatar_url
        val uri = Uri.parse(uriSting)
        holder.binding.apply {
            id.text = pos.id.toString()
            name.text = pos.login
            Glide.with(context).load(uri).into(image)
        }

     }

    override fun getItemCount(): Int  = userList.size

    class UserViewHolder(val binding: UserDataLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}