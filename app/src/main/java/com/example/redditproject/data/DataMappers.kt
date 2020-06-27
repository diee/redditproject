package com.example.redditproject.data

import com.example.domain.FeedData
import com.example.redditproject.data.database.FeedDataEntity
import com.example.redditproject.data.server.ChildrenDataResponse

fun ChildrenDataResponse.toDomainFeedData(): FeedData {
    return FeedData(
        id,
        title,
        author,
        thumbnail,
        comments,
        false,
        created,
        false
    )
}

fun FeedData.toFeedDataEntity(): FeedDataEntity {
    return FeedDataEntity(
        id,
        title,
        author,
        thumbnail,
        comments,
        hasRead,
        entryDate,
        dismissed
    )
}

fun FeedDataEntity.toDomainFeedData(): FeedData {
    return FeedData(
        id,
        title,
        author,
        thumbnail,
        comments,
        hasRead,
        entryDate,
        dismissed
    )
}