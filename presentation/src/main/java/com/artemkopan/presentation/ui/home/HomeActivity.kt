package com.artemkopan.presentation.ui.home

import android.os.Bundle
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseActivity
import com.artemkopan.presentation.dependency.AppInjector
import com.artemkopan.presentation.dependency.Injectable


class HomeActivity : BaseActivity<HomeViewModel>(), Injectable {


    override fun getContentView(): Int = R.layout.activity_home

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun inject(injector: AppInjector) {
        injector.activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getTop().subscribe()
    }

}
