package com.example.redditproject.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.redditproject.R
import com.example.redditproject.common.listenLastItemReached
import com.example.redditproject.ui.detail.DetailActivity
import com.example.redditproject.ui.detail.DetailFragment
import com.example.redditproject.ui.main.MainViewModel.UiModel
import kotlinx.android.synthetic.main.feed_list_component.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lifecycleScope.viewModel(this)

    private lateinit var feedAdapter: FeedAdapter
    private var isDualPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isDualPane = detailContainer != null
        setUpAdapter()
        viewModel.model.observe(this, Observer(::updateUi))
        viewModel.getFeedTop()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.dismissAll -> {
                viewModel.dismissAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpAdapter() {
        feedAdapter = FeedAdapter(viewModel::onFeedItemClicked)
        rvFeed?.adapter = feedAdapter
        swipeFeed?.setOnRefreshListener { viewModel.getFeedTop() }
        rvFeed?.listenLastItemReached { viewModel.getFeedTop() }
    }

    private fun updateUi(model: UiModel) {
        progress?.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE
        swipeFeed?.isRefreshing = false

        when (model) {
            is UiModel.Content -> feedAdapter.feedList = model.feed.toMutableList()
            is UiModel.Navigation -> navigateToDetail(model.feedData.id)
            is UiModel.DismissAll -> feedAdapter.clear()
        }
    }

    private fun navigateToDetail(feedId: String) {
        if (isDualPane) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.detailContainer, DetailFragment.newInstance(feedId))
                .commit()
        } else {
            Intent(this, DetailActivity::class.java)
                .apply { putExtra(DetailFragment.FEED_ID, feedId) }
                .let { startActivity(it) }
        }
    }

    enum class ClickActionType {
        OPEN_DETAIL, DISMISS_FEED
    }
}
