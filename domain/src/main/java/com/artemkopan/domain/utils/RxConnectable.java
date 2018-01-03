package com.artemkopan.domain.utils;

import android.support.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableContainer;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public abstract class RxConnectable<Type, Model> implements Disposable {

    private AtomicBoolean isLoaded = new AtomicBoolean(false);

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
        isLoaded.set(false);
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
        return !isLoaded.get();
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
