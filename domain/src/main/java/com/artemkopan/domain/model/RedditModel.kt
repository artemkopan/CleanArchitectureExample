package com.artemkopan.domain.model

import com.artemkopan.domain.items.PagingRedditItems
import io.reactivex.Single

interface RedditModel {

    fun getTop(limit: Int, after: String): Single<PagingRedditItems>

}