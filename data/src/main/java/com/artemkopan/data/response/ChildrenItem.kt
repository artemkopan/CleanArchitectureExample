package com.artemkopan.data.response

import com.google.gson.annotations.SerializedName

data class ChildrenItem(

        @field:SerializedName("data")
        val data: Data? = null
)