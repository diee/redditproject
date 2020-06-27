package com.example.redditproject.data.server

import com.example.data.source.RemoteDataSource
import com.example.domain.FeedData
import com.example.redditproject.data.toDomainFeedData
import java.lang.Exception

class RedditDataSource(private val redditNetwork: RedditNetwork) : RemoteDataSource {

    private var after: String? = null

    override suspend fun getFeedTop(): List<FeedData> {
        return try {
            val feedDataResponse = redditNetwork.service.getEntries(after).data
            after = feedDataResponse.after
            feedDataResponse.children.map { it.dataResponse.toDomainFeedData() }
        } catch (ex: Exception) {
            //TODO add some error message in UI
            emptyList()
        }
    }
}