package com.artemkopan.presentation.ui.home

import com.artemkopan.domain.interactor.reddit.GetTopUseCase
import com.artemkopan.domain.items.RedditItem
import com.artemkopan.presentation.base.BaseViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getTopUseCase: GetTopUseCase) : BaseViewModel() {


    fun getTop(): Single<List<RedditItem>> {
        return getTopUseCase.execute().observeOn(AndroidSchedulers.mainThread())
    }

}