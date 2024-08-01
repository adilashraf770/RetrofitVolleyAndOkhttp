package com.adilashraf.retrofitandvolley.view


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.retrofitandvolley.adapter.UserAdapter
import com.adilashraf.retrofitandvolley.databinding.ActivityRetrofitBinding
import com.adilashraf.retrofitandvolley.model.UsersItem
import com.adilashraf.retrofitandvolley.retrofitbuilders.UserRetrofitBuilder
import com.tashila.pleasewait.PleaseWaitDialog
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class RetrofitActivity : AppCompatActivity() {
    private val binding: ActivityRetrofitBinding by lazy {
        ActivityRetrofitBinding.inflate(layoutInflater)
    }
    val users = arrayListOf<UsersItem>()
    private var adapter: UserAdapter? = null
    private var dialog: PleaseWaitDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = UserAdapter(users, this@RetrofitActivity)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@RetrofitActivity)
            recyclerView.adapter = adapter
        }



        dialog = PleaseWaitDialog(this)
        dialog!!.setMessage("Loading....")
        dialog!!.isCancelable = false
        dialog!!.show()


        getUserData()

        binding.backImage.setOnClickListener {
            finish()
        }
    }

    private fun getUserData() {
        UserRetrofitBuilder.getInstance().getUsers()
            .enqueue(object : Callback, retrofit2.Callback<List<UsersItem>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    p0: Call<List<UsersItem>>,
                    response: Response<List<UsersItem>>,
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        response.body()!!.forEach {
                            users.add(it)
                        }
                        adapter!!.notifyDataSetChanged()
                        dialog!!.dismiss()
                    }
                }

                override fun onFailure(p0: Call<List<UsersItem>>, p1: Throwable) {}
            })
    }


}



