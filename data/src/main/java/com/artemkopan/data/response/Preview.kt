package com.artemkopan.data.response

import com.google.gson.annotations.SerializedName

data class Preview(

        @field:SerializedName("images")
        val images: List<ImagesItem?>? = null,

        @field:SerializedName("variants")
        val variants: Variants? = null,

        @field:SerializedName("enabled")
        val enabled: Boolean? = null
)