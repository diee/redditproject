package com.example.data.source

import com.example.domain.FeedData

interface RemoteDataSource {
    suspend fun getFeedTop(): List<FeedData>
}