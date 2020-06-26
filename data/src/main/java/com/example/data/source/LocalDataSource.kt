package com.example.data.source

import com.example.domain.FeedData

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveFeed(feedData: List<FeedData>)
    suspend fun getFeed(): List<FeedData>
    suspend fun getFeedDataById(feedId: String): FeedData
}
