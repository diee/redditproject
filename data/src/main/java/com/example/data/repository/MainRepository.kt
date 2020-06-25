package com.example.data.repository

import com.example.data.source.LocalDataSource
import com.example.data.source.RemoteDataSource
import com.example.domain.FeedData

class MainRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getFeedTop(): List<FeedData> {
        if (localDataSource.isEmpty()) {
            val feed = remoteDataSource.getFeedTop()
            localDataSource.saveFeed(feed)
        }
        return localDataSource.getFeed()
    }

    suspend fun refreshFeed(): List<FeedData> {
        val feed = remoteDataSource.getFeedTop()
        localDataSource.saveFeed(feed)
        return localDataSource.getFeed()
    }
}