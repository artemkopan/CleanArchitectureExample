package com.artemkopan.domain.utils;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public class RxConnectableObservable<M> extends RxConnectable<Observable<M>, M> {

    private ConnectableObservable<M> connectableObservable;

    public RxConnectableObservable(ConnectableObservable<M> observable) {
        this.connectableObservable = observable;
    }

    @Override
    public Observable<M> connect(boolean fetch) {
        if (needConnect(fetch)) {
            connectableObservable.connect(getConnectConsumer());
        }
        return connectableObservable.doOnNext(getNextConsumer()).doOnError(getThrowableConsumer());
    }
}
