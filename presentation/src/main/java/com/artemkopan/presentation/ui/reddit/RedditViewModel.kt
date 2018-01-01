package com.artemkopan.presentation.ui.reddit

import com.artemkopan.domain.interactor.reddit.GetTopUseCase
import com.artemkopan.domain.items.RedditItem
import com.artemkopan.presentation.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class RedditViewModel @Inject constructor(private val getTopUseCase: GetTopUseCase) : BaseViewModel() {


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