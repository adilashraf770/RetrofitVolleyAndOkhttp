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