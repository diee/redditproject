package com.example.redditproject.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FeedDataEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val thumbnail: String?,
    val comments: Long,
    var hasRead: Boolean = false,
    val entryDate: Long,
    val dismissed: Boolean = false
)
