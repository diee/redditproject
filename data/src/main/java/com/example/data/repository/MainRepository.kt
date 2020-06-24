package com.example.data.repository

import com.example.data.source.RemoteDataSource
import com.example.domain.FeedData

class MainRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getFeedTop(): List<FeedData> {
        return remoteDataSource.getFeedTop()
    }
}