package com.artemkopan.domain.utils;

import android.support.annotation.CheckResult;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public class RxConnectableObservable<M> extends RxConnectable<Observable<M>, M> {

    private ConnectableObservable<M> connectableObservable;

    private RxConnectableObservable(ConnectableObservable<M> observable) {
        this.connectableObservable = observable;
    }

    @CheckResult(suggest = SUGGEST)
    public static <M> RxConnectableObservable<M> create(ConnectableObservable<M> observable) {
        return new RxConnectableObservable<>(observable);
    }

    @Override
    public Observable<M> connect(boolean fetch) {
        if (needConnect(fetch)) {
            connectableObservable.connect(getConnectConsumer());
        }
        return connectableObservable.doOnNext(getNextConsumer()).doOnError(getThrowableConsumer());
    }
}
