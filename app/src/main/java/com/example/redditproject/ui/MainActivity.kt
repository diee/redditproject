package com.example.redditproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.redditproject.R
import com.example.redditproject.ui.MainViewModel.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lifecycleScope.viewModel(this)

    private lateinit var feedAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAdapter()
        viewModel.model.observe(this, Observer(::updateUi))
        viewModel.getFeedTop()
    }

    private fun setUpAdapter() {
        feedAdapter = FeedAdapter({})
        rvFeed?.adapter = feedAdapter
        swipeFeed?.setOnRefreshListener { viewModel.getFeedTop() }
    }

    private fun updateUi(model: UiModel) {
        progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE
        swipeFeed?.isRefreshing = false

        when (model) {
            is UiModel.Content -> {
                feedAdapter.feedList = model.feed
            }
        }
    }
}
