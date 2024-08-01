package com.adilashraf.retrofitandvolley.retrofitbuilders

import com.adilashraf.retrofitandvolley.interfaces.CommentInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CommentsBuilder {

    val url = "https://jsonplaceholder.typicode.com/comments/"

    fun getInstance(): CommentInterface {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentInterface::class.java)
    }
}