package com.artemkopan.domain.utils

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.observables.ConnectableObservable


//region RxJava
/**
 * @param disposableContainer if this argument is null then you must call #dispose manually
 */
fun <T> ConnectableObservable<T>.delegate(disposableContainer: DisposableContainer?): RxConnectable<Observable<T>, T> {
    return RxConnectableObservable.create(this).addTo(disposableContainer)
}

/**
 * disposable += observable.subscribe()
 */
operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

/**
 * Add the disposable to a CompositeDisposable.
 * @param compositeDisposable CompositeDisposable to add this disposable to
 * @return this instance
 */
fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable
        = apply { compositeDisposable.add(this) }


//endregion