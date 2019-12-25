package com.example.sideproject.ui.main.wining

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.sideproject.data.remote.lottery.WiningPageDataSourceFactory

class WiningViewModel : ViewModel() {

    val winingList = LivePagedListBuilder(WiningPageDataSourceFactory(),
        PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false).build()).build()

    fun refresh() {
        WiningPageDataSourceFactory.sourceMutableLiveData.value?.invalidate()
    }
}