package com.example.sideproject.ui.main.wining

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sideproject.data.remote.ServiceClient
import com.example.sideproject.data.remote.lottery.LotteryRepository

class WiningViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WiningViewModel::class.java)) {
            val service = ServiceClient.getService()
            return WiningViewModel(
                lotteryRepository = LotteryRepository(
                    service = service
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}