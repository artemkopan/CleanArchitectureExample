package com.artemkopan.domain.interactor.reddit

import com.artemkopan.domain.Constants
import com.artemkopan.domain.interactor.UseCase.ObservableUseCase
import com.artemkopan.domain.items.RedditItem
import com.artemkopan.domain.model.RedditModel
import com.artemkopan.domain.utils.Paginator
import com.artemkopan.domain.utils.delegate
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetTopUseCase @Inject constructor(private val redditModel: RedditModel) : ObservableUseCase<List<RedditItem>> {

    private lateinit var paginator: Paginator<RedditItem>

    private val observable by lazy {
        paginator = Paginator()

        Single.fromCallable { paginator.next }
                .subscribeOn(Schedulers.io())
                .flatMap { redditModel.getTop(Constants.LIMIT, it) }
                .map<List<RedditItem>> {
                    paginator.next = it.after ?: ""
                    paginator.addItems(it.items)
                    ArrayList<RedditItem>(paginator.data)
                }
                .toObservable()
                .replay(1)
                .delegate(null)
    }

    fun fetch() {
        observable.dispose()
        paginator.fetch()
    }

    fun next() {
        observable.connect(true)
    }

    override fun execute(): Observable<List<RedditItem>> = observable.connect()

}