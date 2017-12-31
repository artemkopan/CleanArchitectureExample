package com.artemkopan.data.response

import com.google.gson.annotations.SerializedName

data class Variants(

        @field:SerializedName("gif")
        val gif: Gif? = null
)