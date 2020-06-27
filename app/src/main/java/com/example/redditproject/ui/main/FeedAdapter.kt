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
import com.example.redditproject.ui.main.MainActivity.*
import com.example.redditproject.ui.main.MainActivity.ClickActionType.DISMISS_FEED
import com.example.redditproject.ui.main.MainActivity.ClickActionType.OPEN_DETAIL
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter(private val listener: (FeedData, ClickActionType) -> Unit) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    var feedList: MutableList<FeedData> by basicDiffUtil(
        mutableListOf(),
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
        holder.itemView.setOnClickListener {
            listener(feedData, OPEN_DETAIL)
            holder.itemView.ivReadStatus.setImageResource(R.drawable.ic_read)
        }
        holder.itemView.ivDismiss.setOnClickListener {
            listener(feedData, DISMISS_FEED)
            feedList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, feedList.count())
        }
    }

    fun clear() {
        val size = feedList.size
        feedList.clear()
        notifyItemRangeRemoved(0, size)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(feedData: FeedData) {
            with(itemView) {
                tvTitle.text = feedData.title
                tvAuthor.text = context.getString(R.string.author_name, feedData.author)
                tvComments.text = context.getString(R.string.comments_value, feedData.comments)
                tvDate.text = this.context.getEntryTimeAgo(feedData.entryDate)
                feedData.thumbnail?.let { url -> ivThumbnail.loadUrl(url) }
                when (feedData.hasRead) {
                    true -> ivReadStatus.setImageResource(R.drawable.ic_read)
                    else -> ivReadStatus.setImageResource(R.drawable.ic_unread)
                }
            }
        }
    }
}
