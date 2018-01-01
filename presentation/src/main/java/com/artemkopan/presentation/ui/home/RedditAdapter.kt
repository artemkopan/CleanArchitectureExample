package com.artemkopan.presentation.ui.home

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import com.artemkopan.domain.items.RedditItem
import com.artemkopan.presentation.R
import com.artemkopan.recycler.adapter.RecyclerBaseAdapter
import com.artemkopan.recycler.adapter.RecyclerListAdapter
import com.artemkopan.recycler.holder.SimpleHolder
import com.artemkopan.utils.ViewUtils
import javax.inject.Inject

class RedditAdapter @Inject constructor() : RecyclerListAdapter<RedditItem, RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            RecyclerBaseAdapter.FOOTER -> SimpleHolder<Any>(ViewUtils.inflateView(parent, R.layout.item_loading))
            else -> RedditHolder(ViewUtils.inflateView(parent, R.layout.item_reddit))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, model: RedditItem?, position: Int) {
        if (holder is RedditHolder && model != null)
            holder.bind(model, position)
    }

}