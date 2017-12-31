package com.artemkopan.presentation.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.artemkopan.presentation.base.ContentLoadingController.ContentLoadingListener;
import com.artemkopan.utils.ExtraUtils;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public abstract class BaseActivity<V extends ViewModel> extends AppCompatActivity implements ContentLoadingListener {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected CompositeDisposable onStopDisposable = new CompositeDisposable();
    protected CompositeDisposable onDestroyDisposable = new CompositeDisposable();
    protected V viewModel;

    @Inject
    Lazy<ViewModelProvider.Factory> viewModelFactory;
    private PresentationDelegate presentationDelegate;
    private ContentLoadingController contentLoadingController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentationDelegate = new PresentationDelegate(this);
        contentLoadingController = new ContentLoadingController();
        contentLoadingController.setListener(this);
        initViewModel();
        if (getContentView() != View.NO_ID) {
            setContentView(getContentView());
        }
    }

    @Override
    protected void onStop() {
        onStopDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        contentLoadingController.clear();
        onDestroyDisposable.clear();
        ExtraUtils.hideKeyboard(getCurrentFocus());
        super.onDestroy();
    }

    @LayoutRes
    protected abstract int getContentView();

    protected abstract Class<V> getViewModelClass();

    /**
     * If you don't want to init view model, you can override method and stay empty
     */
    protected void initViewModel() {
        if (viewModelFactory == null) {
            Timber.w("ViewModelFactory is null! Please, check your dagger inject logic.");
            return;
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory.get()).get(getViewModelClass());
    }

    public void showError(Throwable throwable) {
        presentationDelegate.showError(throwable);
    }

    public void showError(@Nullable Object tag, Throwable throwable) {
        presentationDelegate.showError(throwable);
    }

    public void showError(@Nullable Object tag, @StringRes int errorRes) {
        presentationDelegate.showError(errorRes);
    }

    public void showError(@Nullable Object tag, String error) {
        presentationDelegate.showError(error);
    }

    public void showProgress() {
        showProgress(null);
    }

    public void showProgress(@Nullable Object tag) {
        presentationDelegate.showProgress();
    }

    public void showContentProgress() {
        contentLoadingController.show();
    }

    public void hideProgress() {
        hideProgress(null);
    }

    public void hideProgress(@Nullable Object tag) {
        presentationDelegate.hideProgress();
    }

    public void hideContentProgress() {
        contentLoadingController.hide();
    }

    protected final void popBackStackAll() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected final int popBackStack() {
        final int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            getSupportFragmentManager().popBackStack();
        }
        return count;
    }

    @Override
    public void onShowContentProgress() {
        showProgress();
    }

    @Override
    public void onHideContentProgress() {
        hideProgress();
    }

}
