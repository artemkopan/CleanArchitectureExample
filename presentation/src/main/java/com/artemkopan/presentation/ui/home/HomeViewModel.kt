package com.artemkopan.presentation.ui.home

import com.artemkopan.domain.entity.RedditItem
import com.artemkopan.domain.interactor.reddit.GetTopUseCase
import com.artemkopan.presentation.base.BaseViewModel
import io.reactivex.Single
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getTopUseCase: GetTopUseCase) : BaseViewModel() {


    fun getTop(): Single<List<RedditItem>> {
        return getTopUseCase.execute()
    }

}