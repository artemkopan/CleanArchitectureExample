package com.artemkopan.data.api

import com.artemkopan.data.response.RedditResponse
import io.reactivex.Single
import retrofit2.http.GET

interface RedditService {

    @GET("top.json")
    fun getTop(): Single<RedditResponse>

}