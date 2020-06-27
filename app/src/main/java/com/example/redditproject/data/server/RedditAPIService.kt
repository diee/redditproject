package com.example.redditproject.data.server

import retrofit2.http.GET
import retrofit2.http.Query

interface RedditAPIService {

    @GET("top.json")
    suspend fun getEntries(@Query("after") after: String? = null): BaseFeedResponse
}