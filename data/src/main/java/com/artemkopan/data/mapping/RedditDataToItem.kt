package com.artemkopan.data.mapping

import com.artemkopan.data.response.RedditResponse.ChildrenItem
import com.artemkopan.domain.items.RedditItem
import com.artemkopan.domain.utils.Mapper
import java.util.concurrent.TimeUnit

class RedditDataToItem : Mapper<ChildrenItem, RedditItem>() {

    override fun map(from: ChildrenItem): RedditItem = with(from.data) {
        RedditItem(
                subredditId,
                RedditDataToPreviewItem().map(this),
                thumbnail,
                author,
                title,
                createdUtc?.let { TimeUnit.SECONDS.toMillis(it) },
                numComments,
                score
        )
    }


}
