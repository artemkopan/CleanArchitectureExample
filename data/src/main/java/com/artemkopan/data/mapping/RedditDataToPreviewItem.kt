package com.artemkopan.data.mapping

import com.artemkopan.data.response.RedditData
import com.artemkopan.data.response.RedditResponse.ChildrenItem
import com.artemkopan.data.utils.FileUtils
import com.artemkopan.domain.items.PreviewItem
import com.artemkopan.domain.utils.Mapper
import com.artemkopan.utils.CollectionUtils
import timber.log.Timber

class RedditDataToPreviewItem : Mapper<RedditData, PreviewItem>() {

    override fun map(from: RedditData): PreviewItem = with(from) {
        var isGif = false
        val preview = preview?.images
                ?.let {
                    if (CollectionUtils.isEmpty(it)) "" else {
                        val img = it[0]?.mediaSource?.url
                        if (FileUtils.isGif(img)) {
                            Timber.d("$from")
                            isGif = true
                            preview.images[0]?.variants?.gif?.mediaSource?.url ?: ""
                        } else {
                            img
                        }
                    } ?: ""
                }
        PreviewItem(preview, isGif)
    }

}