package com.artemkopan.data.api

import com.artemkopan.data.response.RedditResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {

    @GET("top.json")
    fun getTop(@Query("limit") limit: Int, @Query("after") after: String): Single<RedditResponse>

}