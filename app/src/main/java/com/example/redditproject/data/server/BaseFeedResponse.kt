package com.example.redditproject.data.server

import com.google.gson.annotations.SerializedName

data class BaseFeedResponse(val kind: String, val data: FeedDataResponse)

data class FeedDataResponse(val after: String, val before: String, val children: List<DataResponse>)

data class DataResponse(@SerializedName("data") val dataResponse: ChildrenDataResponse)

data class ChildrenDataResponse(
    val id: String,
    val title: String,
    val author: String,
    val thumbnail: String?,
    @SerializedName("num_comments") val comments: Long,
    val created: Long
)