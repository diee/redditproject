package com.example.redditproject

import android.app.Application

class RedditProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}