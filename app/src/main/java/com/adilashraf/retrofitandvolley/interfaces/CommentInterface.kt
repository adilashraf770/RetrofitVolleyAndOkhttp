package com.adilashraf.retrofitandvolley.interfaces

import com.adilashraf.retrofitandvolley.model.CommentsItem
import retrofit2.Call
import retrofit2.http.GET

interface CommentInterface {

    @GET("/comments")
    fun getComments(): Call<List<CommentsItem>>
}