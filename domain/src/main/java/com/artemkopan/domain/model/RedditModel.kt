package com.artemkopan.domain.model

import com.artemkopan.domain.entity.RedditItem
import io.reactivex.Single

interface RedditModel {

    fun getTop(): Single<List<RedditItem>>

}