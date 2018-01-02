package com.artemkopan.domain.items

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class PreviewItem(val url: String?, val isGif: Boolean?) : Parcelable