package com.example.sideproject.data.remote.lottery

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.sideproject.data.model.Winning
import com.example.sideproject.data.remote.ServiceClient

class WiningPageDataSourceFactory : DataSource.Factory<Int, Winning>() {

    companion object {
        val sourceMutableLiveData = MutableLiveData<WiningDataSource>()
    }

    override fun create(): DataSource<Int, Winning> {
        val dataSource = WiningDataSource(
            _repository = LotteryRepository(
                service = ServiceClient.getService()
            )
        )
        sourceMutableLiveData.postValue(dataSource)
        return dataSource
    }
}