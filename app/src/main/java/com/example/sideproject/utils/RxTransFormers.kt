package com.example.sideproject.utils

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object  RxTransFormers {

    fun <T> applySchedulerSingle() : SingleTransformer<T, T> {
        return SingleTransformer{
                upstream ->
                    upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}