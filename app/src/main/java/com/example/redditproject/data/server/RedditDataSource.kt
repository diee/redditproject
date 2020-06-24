package com.example.redditproject.data.server

import com.example.data.source.RemoteDataSource
import com.example.domain.FeedData
import com.example.redditproject.data.toDomainFeedData

class RedditDataSource(private val redditNetwork: RedditNetwork) : RemoteDataSource {

    override suspend fun getFeedTop(): List<FeedData> {
        return redditNetwork.service.getEntries().data.children.map { it.dataResponse.toDomainFeedData() }
    }
}