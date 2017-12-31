package com.artemkopan.data.response

import com.google.gson.annotations.SerializedName

data class MediaSource(

        @field:SerializedName("width")
        val width: Int? = null,

        @field:SerializedName("url")
        val url: String? = null,

        @field:SerializedName("height")
        val height: Int? = null
)