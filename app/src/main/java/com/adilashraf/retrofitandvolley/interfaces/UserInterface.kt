package com.adilashraf.retrofitandvolley.interfaces

import com.adilashraf.retrofitandvolley.model.Users
import com.adilashraf.retrofitandvolley.model.UsersItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface UserInterface {

    @GET("/users")
     fun getUsers(): Call<List<UsersItem>>

}