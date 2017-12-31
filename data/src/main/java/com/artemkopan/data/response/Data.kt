package com.artemkopan.data.response

import com.google.gson.annotations.SerializedName

data class Data(

        @field:SerializedName("preview")
        val preview: Preview? = null,

        @field:SerializedName("score")
        val score: Int? = null,

        @field:SerializedName("num_comments")
        val numComments: Int? = null,

        @field:SerializedName("thumbnail")
        val thumbnail: String? = null,

        @field:SerializedName("author")
        val author: String? = null,

        @field:SerializedName("created")
        val created: Int? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("created_utc")
        val createdUtc: Int? = null,

        @field:SerializedName("url")
        val url: String? = null
)