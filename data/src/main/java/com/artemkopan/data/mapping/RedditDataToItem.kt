package com.artemkopan.data.mapping

import com.artemkopan.data.response.RedditResponse.ChildrenItem
import com.artemkopan.domain.items.RedditItem
import com.artemkopan.domain.utils.Mapper
import com.artemkopan.utils.CollectionUtils

class RedditDataToItem : Mapper<ChildrenItem, RedditItem>() {

    override fun map(from: ChildrenItem): RedditItem = with(from.data) {
        RedditItem(
                subredditId,
                preview?.images?.let { if (CollectionUtils.isEmpty(it)) "" else it[0]?.mediaSource?.url ?: "" },
                thumbnail,
                author,
                title,
                createdUtc?.let { it * 1000 },
                numComments,
                score
        )
    }

}
