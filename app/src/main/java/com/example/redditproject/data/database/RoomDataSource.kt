package com.example.redditproject.data.database

import com.example.data.source.LocalDataSource
import com.example.domain.FeedData
import com.example.redditproject.data.toDomainFeedData
import com.example.redditproject.data.toFeedDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(dataBase: RedditProjectDataBase) : LocalDataSource {

    private val feedDataDAO = dataBase.feedDataDao()

    override suspend fun isEmpty() = withContext(Dispatchers.IO) { feedDataDAO.feedCount() <= 0 }

    override suspend fun saveFeed(feedData: List<FeedData>) {
        withContext(Dispatchers.IO) { feedDataDAO.insertFeed(feedData.map { it.toFeedDataEntity() }) }
    }

    override suspend fun getFeed(): List<FeedData> {
        return withContext(Dispatchers.IO) { feedDataDAO.getAll().map { it.toDomainFeedData() } }
    }
}