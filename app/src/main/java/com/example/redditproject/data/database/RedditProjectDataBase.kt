package com.example.redditproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FeedDataEntity::class], version = 1)
abstract class RedditProjectDataBase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            RedditProjectDataBase::class.java,
            "reddit-challenge-db"
        ).build()
    }

    abstract fun feedDataDao(): FeedDataDAO
}