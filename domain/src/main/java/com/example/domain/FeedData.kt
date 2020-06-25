package com.example.domain

data class FeedData(
    val id: String,
    val title: String,
    val author: String,
    val thumbnail: String?,
    val comments: Long,
    var hasRead: Boolean = false,
    val entryDate: Long
)