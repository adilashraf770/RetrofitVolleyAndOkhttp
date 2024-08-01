package com.adilashraf.retrofitandvolley.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.retrofitandvolley.adapter.UserAdapter
import com.adilashraf.retrofitandvolley.databinding.ActivityVolleyBinding
import com.adilashraf.retrofitandvolley.model.Users
import com.adilashraf.retrofitandvolley.model.UsersItem
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.tashila.pleasewait.PleaseWaitDialog

class VolleyActivity : AppCompatActivity() {
    private val binding: ActivityVolleyBinding by lazy {
        ActivityVolleyBinding.inflate(layoutInflater)
    }
    val url = "https://api.github.com/users"
    var userItems = arrayOf<UsersItem>()
    var users = Users()

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
            userItems = gson.fromJson(it, Array<UsersItem>::class.java)

            userItems.forEach { d ->
                users.add(d)
            }
            setAdapter(users)
            dialog!!.dismiss()
         }, {
            Toast.makeText(this, "${it.message}",Toast.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(this).add(stringReq)


        binding.backImage.setOnClickListener{
            finish()
        }
    }

    private fun setAdapter(users: Users) {
        val adapter = UserAdapter(users, this)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@VolleyActivity)
            recyclerView.adapter = adapter

        }
    }
}