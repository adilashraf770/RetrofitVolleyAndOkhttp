package com.adilashraf.retrofitandvolley.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.retrofitandvolley.adapter.CommentAdapter
import com.adilashraf.retrofitandvolley.databinding.ActivityCommentUsingVolleyBinding
import com.adilashraf.retrofitandvolley.model.Comments
import com.adilashraf.retrofitandvolley.model.CommentsItem
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.tashila.pleasewait.PleaseWaitDialog

class CommentUsingVolleyActivity : AppCompatActivity() {
    val binding: ActivityCommentUsingVolleyBinding by lazy {
        ActivityCommentUsingVolleyBinding.inflate(layoutInflater)
    }

    val url = "https://jsonplaceholder.typicode.com/comments"
    val comments = Comments()
    var commentItems = arrayOf<CommentsItem>()
    var dialog: PleaseWaitDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dialog = PleaseWaitDialog(this)
        dialog!!.setMessage("Loading....")
        dialog!!.isCancelable = false
        dialog!!.show()

        val stringReq = StringRequest(url, {
            val gson = GsonBuilder().create()
            commentItems = gson.fromJson(it, Array<CommentsItem>::class.java)

            commentItems.forEach {d->
                comments.add(d)
            }
            setAdapter(comments)
            dialog!!.dismiss()

        },{
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(this).add(stringReq)

        binding.backImage.setOnClickListener{
            finish()
        }
    }

    private fun setAdapter(comments: Comments) {
        val adapter = CommentAdapter(comments, this)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@CommentUsingVolleyActivity)
            recyclerView.adapter = adapter
        }
    }
}