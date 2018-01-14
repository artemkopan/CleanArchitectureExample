package com.artemkopan.presentation.ui.media

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import com.artemkopan.domain.Constants.Keys
import com.artemkopan.domain.items.PreviewItem
import com.artemkopan.presentation.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_media_preview.*
import timber.log.Timber

class MediaPreviewActivity : Activity() {

    companion object {
        fun show(activity: Activity, url: Preview?) {
            val intent = Intent(activity, MediaPreviewActivity::class.java)
            intent.putExtra(Keys.MEDIA, url)
            activity.startActivity(intent)
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_preview)

        intent.getParcelableExtra<Preview>(Keys.MEDIA)?.let {
            with(if (it.isGif == true)
                     Glide.with(this).asGif().listener(RequestListenerImpl())
                 else
                     Glide.with(this).asDrawable().listener(RequestListenerImpl())) {
                val options = RequestOptions()
                options.fitCenter()
                options.error(R.drawable.ic_image_broken_grey_24dp)
                apply(options)
                load(it.url)
                into(mediaImageView)
            }
        }
    }


    private inner class RequestListenerImpl<R> : RequestListener<R> {
        override fun onResourceReady(resource: R, model: Any?, target: Target<R>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            progressBar.visibility = GONE
            return false
        }

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<R>?, isFirstResource: Boolean): Boolean {
            progressBar.visibility = GONE
            return false
        }
    }
}