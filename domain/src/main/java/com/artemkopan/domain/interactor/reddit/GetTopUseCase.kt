package com.artemkopan.domain.interactor.reddit

import com.artemkopan.domain.items.RedditItem
import com.artemkopan.domain.interactor.UseCase.SingleUseCase
import com.artemkopan.domain.model.RedditModel
import io.reactivex.Single
import javax.inject.Inject

class GetTopUseCase @Inject constructor(private val redditModel: RedditModel) : SingleUseCase<List<RedditItem>> {

    override fun execute(): Single<List<RedditItem>> = redditModel.getTop()

}