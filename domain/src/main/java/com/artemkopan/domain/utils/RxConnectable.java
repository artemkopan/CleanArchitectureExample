package com.artemkopan.domain.utils;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public abstract class RxConnectable<Type, Model> {

    private AtomicBoolean isLoaded = new AtomicBoolean(false);
    private Disposable disposable;

    protected RxConnectable() {}

    public Type connect() {
        return connect(false);
    }

    public abstract Type connect(boolean fetch);

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    protected boolean needConnect(boolean fetch) {
        if (fetch) {
            dispose();
            return true;
        }
        return disposable == null || !isLoaded.get();
    }

    protected Consumer<Disposable> getConnectConsumer() {
        return new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                RxConnectable.this.disposable = disposable;
            }
        };
    }

    protected Consumer<Model> getNextConsumer() {
        return new Consumer<Model>() {
            @Override
            public void accept(Model model) throws Exception {
                isLoaded.set(true);
            }
        };
    }

    protected Consumer<Throwable> getThrowableConsumer() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                isLoaded.set(false);
            }
        };
    }

}
