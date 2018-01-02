package com.artemkopan.data.utils

import android.net.Uri

object FileUtils {


    @JvmStatic
    fun isGif(path: String?): Boolean {
        if (path == null) return false
        val uri = Uri.parse(path)
        val segments = uri.pathSegments
        return if (segments.isNotEmpty()) {
            segments[segments.lastIndex].endsWith("gif", true)
        } else {
            false
        }
    }

}