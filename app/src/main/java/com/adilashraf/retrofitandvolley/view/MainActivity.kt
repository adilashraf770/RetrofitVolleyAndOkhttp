package com.adilashraf.retrofitandvolley.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adilashraf.retrofitandvolley.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            volleyUserData.setOnClickListener {
                val i = Intent(this@MainActivity, VolleyActivity::class.java)
                startActivity(i)

            }
            retrofitUserData.setOnClickListener {
                val i = Intent(this@MainActivity, RetrofitActivity::class.java)
                startActivity(i)
            }

            volleyComments.setOnClickListener {
                val i = Intent(this@MainActivity, CommentUsingVolleyActivity::class.java)
                startActivity(i)
            }

            retrofitComments.setOnClickListener {
                val i = Intent(this@MainActivity, CommentsUsingRetrofitActivity::class.java)
                startActivity(i)
            }
        }
    }
}