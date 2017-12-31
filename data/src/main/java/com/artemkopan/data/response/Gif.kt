package com.artemkopan.data.response

import com.google.gson.annotations.SerializedName

data class Gif(

        @field:SerializedName("source")
        val mediaSource: MediaSource? = null
)