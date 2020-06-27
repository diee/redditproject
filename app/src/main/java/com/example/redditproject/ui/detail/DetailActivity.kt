package com.example.redditproject.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.redditproject.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.detailContainer, DetailFragment())
            .commit()
    }
}
