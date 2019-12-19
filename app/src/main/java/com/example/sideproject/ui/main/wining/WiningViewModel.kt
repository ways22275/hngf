package com.example.sideproject.ui.main.wining

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sideproject.data.model.Winning
import com.example.sideproject.data.remote.lottery.LotteryRepository
import com.example.sideproject.utils.RxTransFormers
import io.reactivex.disposables.CompositeDisposable

class WiningViewModel(private var lotteryRepository: LotteryRepository) : ViewModel() {

    private val _winingList = MutableLiveData<ArrayList<Winning>>()
    val winingList: LiveData<ArrayList<Winning>> = _winingList

    private val _compositeDisposable = CompositeDisposable()
    var page = 1
        private set

    fun getList() {
        page++
        val disposable = lotteryRepository
            .getWinningList()
            .compose(RxTransFormers.applySchedulerSingle())
            .subscribe(
                {
                        response -> _winingList.value = response.data
                },
                {
                        e -> e.printStackTrace()
                })
        _compositeDisposable.add(disposable)
    }

    fun resetPageCount() {
        page = 1
    }

    override fun onCleared() {
        super.onCleared()
        _compositeDisposable.dispose()
    }
}