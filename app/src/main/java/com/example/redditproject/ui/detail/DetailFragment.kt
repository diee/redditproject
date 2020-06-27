package com.example.redditproject.ui.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.redditproject.common.getEntryTimeAgo
import com.example.redditproject.R
import com.example.redditproject.common.loadUrl
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.detailContainer
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {

    private var feedIdValue: String? = null
    private var thumbnailUrl: String? = null

    companion object {
        const val FEED_ID = "feed_data_id"
        fun newInstance(feedId: String) = DetailFragment().apply {
            feedIdValue = feedId
        }
    }

    private val viewModel: DetailViewModel by lifecycleScope.viewModel(this) {
        when (feedIdValue) {
            null -> parametersOf(activity?.intent?.getStringExtra(FEED_ID))
            else -> parametersOf(feedIdValue)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
        fabSaveImage?.setOnClickListener { saveImage() }
    }

    private fun updateUi(model: DetailViewModel.UiModel) {
        with(model) {
            viewModel.setAsRead(feedData)
            feedData.thumbnail?.let {
                thumbnailUrl = it
                ivThumbnail?.loadUrl(it)
                ivThumbnail?.setOnClickListener { openImageView() }
                fabSaveImage?.visibility = View.VISIBLE
            }
            tvAuthor?.text = getString(R.string.author_name, feedData.author)
            tvTitle?.text = feedData.title
            tvEntryDate?.text = context?.getEntryTimeAgo(feedData.entryDate)
        }
    }

    private fun saveImage() {
        Glide
            .with(this)
            .asBitmap()
            .load(thumbnailUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    MediaStore.Images.Media.insertImage(
                        activity?.contentResolver,
                        resource,
                        "reddit-img",
                        ""
                    )
                    Snackbar.make(
                        detailContainer,
                        "Image downloaded!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun openImageView() {
        thumbnailUrl?.let {
            startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(it) })
        }
    }
}
