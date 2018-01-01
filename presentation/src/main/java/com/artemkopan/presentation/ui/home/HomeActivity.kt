package com.artemkopan.presentation.ui.home

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseActivity
import com.artemkopan.presentation.dependency.AppInjector
import com.artemkopan.presentation.dependency.Injectable
import com.artemkopan.recycler.listeners.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE


class HomeActivity : BaseActivity<HomeViewModel>(), Injectable {

    @Inject lateinit var adapter: RedditAdapter

    override fun getContentView(): Int = R.layout.activity_home

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun inject(injector: AppInjector) {
        injector.activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redditRecyclerView.layoutManager = LinearLayoutManager(this)
        redditRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        redditRecyclerView.addOnScrollListener(endlessScrollListener)
        redditRecyclerView.adapter = adapter


        loadTop()
    }

    private val endlessScrollListener by lazy(NONE) {
        object : EndlessRecyclerViewScrollListener(redditRecyclerView.layoutManager) {
            override fun onLoadMore(totalItemsCount: Int) {

            }
        }
    }

    private fun loadTop() {
        viewModel
                .getTop()
                .subscribe({
                               swipeRefresh.isRefreshing = false
                               adapter.list = it
                           }, { showError(it) })
    }
}
