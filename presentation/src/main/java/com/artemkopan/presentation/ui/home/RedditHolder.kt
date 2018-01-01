package com.artemkopan.presentation.ui.home

import android.content.Context
import android.view.View
import com.artemkopan.domain.items.RedditItem
import com.artemkopan.presentation.R
import com.artemkopan.presentation.utils.loadClear
import com.artemkopan.presentation.utils.loadImage
import com.artemkopan.recycler.holder.BaseHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_reddit.*
import kotlin.LazyThreadSafetyMode.NONE

class RedditHolder(override val containerView: View) : BaseHolder<RedditItem>(containerView), LayoutContainer {

    private val cornerRadius by lazy(NONE) { containerView.context.resources.getDimensionPixelSize(R.dimen.corner) }

    override fun bind(context: Context, item: RedditItem, position: Int) {
        with(item) {
            thumbnailImageView.loadImage(model = thumbnail,
                                         animate = true,
                                         transformations = *arrayOf(RoundedCornersTransformation(cornerRadius, 0)))
            authorTextView.text = author
            timeTextView.itemTime = createdAt ?: 0
            titleTextView.text = title
            scoreTextView.text = score.toString()
            commentsTextView.text = commentCount.toString()
        }
    }

    override fun clear() {
        thumbnailImageView.loadClear()
    }

}