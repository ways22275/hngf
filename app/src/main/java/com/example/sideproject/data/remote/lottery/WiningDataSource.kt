package com.example.sideproject.data.remote.lottery

import android.annotation.SuppressLint
import androidx.paging.PageKeyedDataSource
import com.example.sideproject.data.model.Winning
import com.example.sideproject.utils.RxTransFormers

class WiningDataSource(private val _repository: LotteryRepository) : PageKeyedDataSource<Int, Winning>() {

    private var _page = 1

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Winning>) {
        _repository.getWinningList(_page)
            .compose(RxTransFormers.applySchedulerSingle())
            .subscribe(
                {
                    _page++
                    it.data?.let { dataList -> callback.onResult(dataList, 0, _page) }
                },
                { e -> e.printStackTrace() }
            )
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Winning>) {
        _repository.getWinningList(params.key)
            .compose(RxTransFormers.applySchedulerSingle())
            .subscribe(
                {
                    it.data?.let { dataList -> callback.onResult(dataList, params.key + 1) }
                },
                { e -> e.printStackTrace() }
            )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Winning>) {}
}