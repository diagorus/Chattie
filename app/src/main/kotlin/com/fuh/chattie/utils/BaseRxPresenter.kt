package com.fuh.chattie.utils

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by lll on 23.08.2017.
 */
abstract class BaseRxPresenter : BasePresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun stop() {
        compositeDisposable.clear()
    }
}