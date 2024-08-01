package com.adilashraf.retrofitandvolley.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.retrofitandvolley.adapter.UserAdapter
import com.adilashraf.retrofitandvolley.databinding.ActivityOkhttpBinding
import com.adilashraf.retrofitandvolley.model.Users
import com.adilashraf.retrofitandvolley.model.UsersItem
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

@Suppress("CAST_NEVER_SUCCEEDS")
class OkhttpActivity : AppCompatActivity() {
    val binding: ActivityOkhttpBinding by lazy {
        ActivityOkhttpBinding.inflate(layoutInflater)
    }
    val url = "https://api.github.com/users"
    val client = OkHttpClient()
    val users = arrayListOf<UsersItem>()
    var adapter: UserAdapter? = null

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = UserAdapter(users, this)
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@OkhttpActivity)
        }

        binding.backImage.setOnClickListener {
            finish()
        }


        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                Log.e("myError", "${e.message}")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {

                if (response.isSuccessful && response.body != null) {
                    val result =
                        GsonBuilder().create()
                            .fromJson(response.body.toString(), Users::class.java)
                    result.forEach {
                        users.add(it)
                    }
                    adapter!!.notifyDataSetChanged()
                }
            }
        })

    }


}

