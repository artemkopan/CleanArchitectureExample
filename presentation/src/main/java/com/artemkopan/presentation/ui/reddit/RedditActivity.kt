package com.artemkopan.presentation.ui.reddit

import android.os.Bundle
import android.os.Parcelable
import android.support.transition.Slide
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import com.artemkopan.domain.Constants.Keys
import com.artemkopan.domain.utils.addTo
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseActivity
import com.artemkopan.presentation.dependency.AppInjector
import com.artemkopan.presentation.dependency.Injectable
import com.artemkopan.presentation.ui.media.MediaPreviewActivity
import com.artemkopan.recycler.listeners.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_reddit.*
import timber.log.Timber
import java.util.concurrent.Callable
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE


class RedditActivity : BaseActivity<RedditViewModel>(), Injectable {

    @Inject lateinit var adapter: RedditAdapter
    var managerState: Parcelable? = null

    override fun getContentView(): Int = R.layout.activity_reddit

    override fun getViewModelClass(): Class<RedditViewModel> = RedditViewModel::class.java

    override fun inject(injector: AppInjector) {
        injector.activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        managerState = savedInstanceState?.getParcelable(Keys.LAYOUT_MANAGER)

        redditRecyclerView.layoutManager = LinearLayoutManager(this)
        redditRecyclerView.addOnScrollListener(endlessScrollListener)
        redditRecyclerView.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            viewModel.reset()
            loadItems()
        }

        adapter.setOnItemClickListener { _, _, item, _ ->
            MediaPreviewActivity.show(this, item.preview)
        }
    }


    override fun onStart() {
        super.onStart()
        loadItems()

        viewModel.test.connect().subscribe({ Timber.d(it.toString()) })
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
        showProgress()

        viewModel
                .loadItems()
                .doOnError { hideProgress(); endlessScrollListener.enable(true) }
                .subscribe({
                               hideProgress()
                               if (managerState != null) {
                                   adapter.setList(it, true)
                                   redditRecyclerView.layoutManager.onRestoreInstanceState(managerState)
                                   managerState = null
                               } else {
                                   val isEmpty = adapter.isEmpty
                                   adapter.list = it
                                   if (isEmpty) {
                                       TransitionManager.beginDelayedTransition(redditRecyclerView, Slide(Gravity.BOTTOM))
                                   }
                               }

                               endlessScrollListener.enable(true)
                           }, { showError(it) })
                .addTo(onStopDisposable)
    }

    override fun showProgress() {
        if (adapter.isEmpty && !swipeRefresh.isRefreshing) redditRecyclerView.showProgress()
        else if (!adapter.isEmpty) adapter.showFooter(true)
    }

    override fun hideProgress() {
        adapter.showFooter(false)
        redditRecyclerView.hideProgress()
        swipeRefresh.isRefreshing = false
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(Keys.LAYOUT_MANAGER, redditRecyclerView.layoutManager.onSaveInstanceState())
    }
}
