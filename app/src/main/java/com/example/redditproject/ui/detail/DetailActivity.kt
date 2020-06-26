package com.example.redditproject.ui.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.redditproject.R
import com.example.redditproject.common.getEntryTimeAgo
import com.example.redditproject.common.loadUrl
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {

    companion object {
        const val FEED_ID = "feed_data_id"
    }

    var thumbnailUrl: String? = null

    private val viewModel: DetailViewModel by lifecycleScope.viewModel(this) {
        parametersOf(intent.getStringExtra(FEED_ID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel.model.observe(this, Observer(::updateUi))
        fabSaveImage?.setOnClickListener { saveImage() }
    }

    private fun updateUi(model: DetailViewModel.UiModel) {
        viewModel.setAsRead()
        with(model) {
            feedData.thumbnail?.let {
                thumbnailUrl = it
                ivThumbnail.loadUrl(it)
                ivThumbnail.setOnClickListener { openImageView() }
            }
            tvAuthor.text = getString(R.string.author_name, feedData.author)
            tvTitle.text = feedData.title
            tvEntryDate.text = getEntryTimeAgo(feedData.entryDate)
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
                    MediaStore.Images.Media.insertImage(contentResolver, resource, "reddit-img", "")
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
