package com.artemkopan.data.model

import com.artemkopan.data.api.RedditService
import com.artemkopan.data.mapping.RedditDataToItem
import com.artemkopan.domain.items.PagingRedditItems
import com.artemkopan.domain.model.RedditModel
import com.artemkopan.utils.CollectionUtils
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class RedditDataModel @Inject constructor(private val redditService: RedditService) : RedditModel {

    override fun getTop(limit: Int, after: String): Single<PagingRedditItems> {
        return redditService.getTop(limit, after)
                .map {
                    val list = it.data?.childrenItem
                    if (CollectionUtils.isEmpty(list))
                        PagingRedditItems(null, Collections.emptyList())
                    else
                        PagingRedditItems(it.data?.after, RedditDataToItem().mapList(list))
                }
    }


}