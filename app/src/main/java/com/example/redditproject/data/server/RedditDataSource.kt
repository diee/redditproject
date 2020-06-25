package com.example.redditproject.data.server

import com.example.data.source.RemoteDataSource
import com.example.domain.FeedData
import com.example.redditproject.data.toDomainFeedData
import java.lang.Exception

class RedditDataSource(private val redditNetwork: RedditNetwork) : RemoteDataSource {

    override suspend fun getFeedTop() = try {
        redditNetwork.service.getEntries().data.children
            .map { it.dataResponse.toDomainFeedData() }
    } catch (ex: Exception) {
        //TODO add some error message in UI
        emptyList<FeedData>()
    }
}