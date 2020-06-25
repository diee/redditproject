package com.example.redditproject.data.database

import androidx.room.*

@Dao
interface FeedDataDAO {

    @Query("SELECT * FROM FeedDataEntity")
    fun getAll(): List<FeedDataEntity>

    @Query("SELECT * FROM FeedDataEntity WHERE id = :id")
    fun findById(id: String): FeedDataEntity

    @Query("SELECT COUNT(id) FROM FeedDataEntity")
    fun feedCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFeed(movies: List<FeedDataEntity>)

    @Update
    fun updateFeed(feedData: FeedDataEntity)
}
