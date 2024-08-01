# Retrofit, Volley and Okhttp Netorking libraries

###  Dependecies

```xml

dependencies {
...

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Volley
    implementation("com.android.volley:volley:1.2.1")
     // Gson Convertor
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // GLide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Circle Image
    implementation("de.hdodenhof:circleimageview:3.1.0")
    // OkaHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    // Please Wait Dialog
    implementation("io.github.tashilapathum:please-wait:0.5.0")

...
}
```
### Add User Permission in Menifest File

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### Design your Activity which should look like this:
 
 ```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center_horizontal"
     tools:context=".view.VolleyActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:gravity="center_vertical"
        >
        <ImageView
            android:id="@+id/backImage"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp"
            android:src="@drawable/baseline_arrow_back_24"
            />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Users Api Data Using Volley"
            android:textSize="18sp"
            android:textAlignment="center"
              android:layout_marginVertical="10dp"
            />
    </LinearLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
</LinearLayout>

```

### Api URL

```xml
 https://api.github.com/users
```


## Retrofit
Now let's create data model class, interface and retrofit builder class for Retrofit

### Data Model Class
**UsersItem.kt**

```kotlin

package com.adilashraf.retrofitandvolley.model
data class UsersItem(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)


```

**Users.kt**

```kotlin
 package com.adilashraf.retrofitandvolley.model

class Users : ArrayList<UsersItem>()
```


### Interface
**UserInterface.kt**

```kotlin
package com.adilashraf.retrofitandvolley.interfaces

 import com.adilashraf.retrofitandvolley.model.UsersItem
import retrofit2.Call
 import retrofit2.http.GET

interface UserInterface {

    @GET("/users")
    fun getUsers(): Call<List<UsersItem>>

}

```

### Retrofit Builder 
**UserRetrofitBuilder.kt**

```kotlin
package com.adilashraf.retrofitandvolley.retrofitbuilders

import com.adilashraf.retrofitandvolley.interfaces.UserInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object UserRetrofitBuilder {

    val URL = "https://api.github.com/"

    fun getInstance(): UserInterface {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserInterface::class.java)
    }

}

```

 
### Retrofit Activity Code
**RetrofitActivity.kt**

```kotlin
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

```
 
## Volley
 
**VolleyActivity.kt**

```kotlin
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
            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(this).add(stringReq)


        binding.backImage.setOnClickListener {
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
```
## OkHttp
 
**OkhttpActivity.kt**

```kotlin
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


````

For any clarifications please refer to the repository.

## **Conclusion**

Hopefully this guide will introduce you to about useful Networking libraries such as Retrofit, Volley and Okhttp for fetching from data from Api.

 
