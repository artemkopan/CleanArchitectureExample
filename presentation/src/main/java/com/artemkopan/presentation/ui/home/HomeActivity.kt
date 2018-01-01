package com.artemkopan.presentation.ui.home

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.artemkopan.domain.Constants.Keys
import com.artemkopan.domain.utils.addTo
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
    var managerState: Parcelable? = null

    override fun getContentView(): Int = R.layout.activity_home

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun inject(injector: AppInjector) {
        injector.activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        managerState = savedInstanceState?.getParcelable<Parcelable>(Keys.LAYOUT_MANAGER)

        redditRecyclerView.layoutManager = LinearLayoutManager(this)
        redditRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        redditRecyclerView.addOnScrollListener(endlessScrollListener)
        redditRecyclerView.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            viewModel.reset()
            loadItems()
        }
    }

    override fun onStart() {
        super.onStart()
        loadItems()
    }

    private val endlessScrollListener by lazy(NONE) {
        object : EndlessRecyclerViewScrollListener(redditRecyclerView.layoutManager) {
            override fun onLoadMore(totalItemsCount: Int) {
                viewModel.next()
                loadItems()
            }
        }
    }

    private fun loadItems() {
        endlessScrollListener.enable(false)
        if (adapter.isEmpty && !swipeRefresh.isRefreshing) redditRecyclerView.showProgress()
        else if (!adapter.isEmpty) adapter.showFooter(true)

        viewModel
                .loadItems()
                .subscribe({
                               adapter.showFooter(false)
                               redditRecyclerView.hideProgress()
                               swipeRefresh.isRefreshing = false

                               if (managerState != null) {
                                   adapter.setList(it, true)
                                   redditRecyclerView.layoutManager.onRestoreInstanceState(managerState)
                                   managerState = null
                               } else {
                                   adapter.list = it
                               }

                               endlessScrollListener.enable(true)
                           }, { showError(it) })
                .addTo(onStopDisposable)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(Keys.LAYOUT_MANAGER, redditRecyclerView.layoutManager.onSaveInstanceState())
    }
}
