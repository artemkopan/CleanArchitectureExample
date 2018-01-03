package com.artemkopan.presentation.ui.reddit

import com.artemkopan.domain.interactor.reddit.GetTopUseCase
import com.artemkopan.domain.items.RedditItem
import com.artemkopan.domain.utils.RxConnectableObservable
import com.artemkopan.domain.utils.addTo
import com.artemkopan.presentation.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.disposables.DisposableContainer
import java.util.concurrent.Callable
import javax.inject.Inject

class RedditViewModel @Inject constructor(private val getTopUseCase: GetTopUseCase) : BaseViewModel() {


    private var counter: Int = 1

    val test by lazy {
        RxConnectableObservable(Observable.fromCallable { ++counter }.replay(1)).apply { addTo(onClearedDisposable) }
    }

    fun reset() {
        getTopUseCase.fetch()
    }

    fun next() {
        getTopUseCase.next()
    }

    fun loadItems(): Observable<List<RedditItem>> {
        return getTopUseCase.execute().observeOn(AndroidSchedulers.mainThread())
    }

    override fun onCleared() {
        getTopUseCase.fetch()
        super.onCleared()
    }
}