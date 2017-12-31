package com.artemkopan.data.model

import com.artemkopan.data.api.RedditService
import com.artemkopan.domain.entity.RedditItem
import com.artemkopan.domain.model.RedditModel
import io.reactivex.Single
import javax.inject.Inject

class RedditDataModel @Inject constructor(private val redditService: RedditService) : RedditModel {

    override fun getTop(): Single<List<RedditItem>> {
        return redditService.getTop().flatMap { Single.never<List<RedditItem>>() }
    }

}