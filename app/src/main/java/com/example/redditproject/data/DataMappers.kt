package com.example.redditproject.data

import com.example.domain.FeedData
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