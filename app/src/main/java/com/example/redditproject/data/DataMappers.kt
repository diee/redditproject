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
        created
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
        entryDate
    )
}

fun FeedDataEntity.toDomainFeedData(): FeedData {
    return FeedData(
        id,
        title,
        author,
        thumbnail,
        comments,
        false,
        entryDate
    )
}