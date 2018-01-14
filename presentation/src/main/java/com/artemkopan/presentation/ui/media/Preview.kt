package com.artemkopan.presentation.ui.media

import android.annotation.SuppressLint
import android.os.Parcelable
import com.artemkopan.domain.items.PreviewItem
import com.artemkopan.domain.utils.Mapper
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Preview(val url: String?, val isGif: Boolean?) : Parcelable


class PreviewItemToPreview : Mapper<PreviewItem, Preview>() {
    override fun map(from: PreviewItem): Preview = with(from) {
        Preview(url, isGif)
    }
}