package com.artemkopan.domain.items

data class RedditItem constructor(val id: String?,
                                  val preview: PreviewItem?,
                                  val thumbnail: String?,
                                  val author: String?,
                                  val title: String?,
                                  val createdAt: Long?,
                                  val commentCount: Int?,
                                  val score: Int?)