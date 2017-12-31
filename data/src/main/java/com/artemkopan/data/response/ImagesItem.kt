package com.artemkopan.data.response

import com.google.gson.annotations.SerializedName

data class ImagesItem(

        @field:SerializedName("source")
        val mediaSource: MediaSource? = null,

        @field:SerializedName("id")
        val id: String? = null
)