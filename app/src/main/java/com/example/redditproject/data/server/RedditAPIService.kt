package com.example.redditproject.data.server

import retrofit2.http.GET

interface RedditAPIService {

    @GET("top.json")
    suspend fun getEntries(): BaseFeedResponse
}