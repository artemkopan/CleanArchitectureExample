package com.artemkopan.data.response

import com.google.gson.annotations.SerializedName

data class RedditResponse(
        @field:SerializedName("data")
        val data: InnerData? = null
) {

    data class InnerData(
            @field:SerializedName("children")
            val childrenItem: List<ChildrenItem>? = null
    )

    data class ChildrenItem(
            @field:SerializedName("data")
            val data: RedditData
    )


}