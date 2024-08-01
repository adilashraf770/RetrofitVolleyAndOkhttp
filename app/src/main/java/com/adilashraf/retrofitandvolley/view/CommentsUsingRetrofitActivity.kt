package com.adilashraf.retrofitandvolley.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.retrofitandvolley.adapter.CommentAdapter
import com.adilashraf.retrofitandvolley.databinding.ActivityCommentsUsingRetrofitBinding
import com.adilashraf.retrofitandvolley.model.CommentsItem
import com.adilashraf.retrofitandvolley.retrofitbuilders.CommentsBuilder
import com.tashila.pleasewait.PleaseWaitDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CommentsUsingRetrofitActivity : AppCompatActivity() {
    val binding: ActivityCommentsUsingRetrofitBinding by lazy {
        ActivityCommentsUsingRetrofitBinding.inflate(layoutInflater)
    }

    val comments = arrayListOf<CommentsItem>()
    var adapter: CommentAdapter? = null
    var dialog: PleaseWaitDialog? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = CommentAdapter(comments, this)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@CommentsUsingRetrofitActivity)
            recyclerView.adapter = adapter
        }

        dialog = PleaseWaitDialog(this)
        dialog!!.setMessage("Loading...")
        dialog!!.isCancelable = false
        dialog!!.show()
        getCommentApiData()

        binding.backImage.setOnClickListener {
            finish()
        }


    }

    private fun getCommentApiData() {
        CommentsBuilder.getInstance().getComments()
            .enqueue(object : Callback, retrofit2.Callback<List<CommentsItem>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    p0: Call<List<CommentsItem>>,
                    p1: Response<List<CommentsItem>>,
                ) {
                    if (p1.isSuccessful && p1.body() != null) {
                        p1.body()!!.forEach {
                            comments.add(it)
                        }
                        adapter!!.notifyDataSetChanged()
                        dialog!!.dismiss()
                    }
                }

                override fun onFailure(p0: Call<List<CommentsItem>>, p1: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }


}
