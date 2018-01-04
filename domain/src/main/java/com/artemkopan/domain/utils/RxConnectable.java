package com.artemkopan.domain.utils;

import android.support.annotation.Nullable;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableContainer;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public abstract class RxConnectable<Type, Model> implements Disposable {

    protected static final String SUGGEST =  "#addTo(CompositeDisposable)";

    private final Object lock = new Object();
    private volatile boolean isLoaded = false;

    private Disposable disposable;

    protected RxConnectable() {}

    public Type connect() {
        return connect(false);
    }

    public abstract Type connect(boolean fetch);

    public RxConnectable<Type, Model> addTo(@Nullable DisposableContainer container) {
        if (container != null) container.add(this);
        return this;
    }

    @Override
    public void dispose() {
        synchronized (lock) {
            isLoaded = false;
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    public boolean isDisposed() {
        return false;
    }

    protected boolean needConnect(boolean fetch) {
        if (fetch) {
            dispose();
            return true;
        }
        return !isLoaded;
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
                synchronized (lock) {
                    isLoaded = true;
                }
            }
        };
    }

    protected Consumer<Throwable> getThrowableConsumer() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                synchronized (lock) {
                    isLoaded = false;
                }
            }
        };
    }
}
