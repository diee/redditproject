package com.example.redditproject.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.FeedData
import com.example.redditproject.R
import com.example.redditproject.common.basicDiffUtil
import com.example.redditproject.common.getEntryTimeAgo
import com.example.redditproject.common.inflate
import com.example.redditproject.common.loadUrl
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter(private val listener: (FeedData) -> Unit) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    var feedList: List<FeedData> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.feed_item, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = feedList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedData = feedList[position]
        holder.bind(feedData)
        holder.itemView.setOnClickListener { listener(feedData) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(feedData: FeedData) {
            with(itemView) {
                tvTitle.text = feedData.title
                tvAuthor.text = context.getString(R.string.author_name, feedData.author)
                tvComments.text = context.getString(R.string.comments_value, feedData.comments)
                tvReadStatus.text = "Unread"
                tvDate.text = this.context.getEntryTimeAgo(feedData.entryDate)
                feedData.thumbnail?.let { url -> ivThumbnail.loadUrl(url) }
            }
        }
    }
}
