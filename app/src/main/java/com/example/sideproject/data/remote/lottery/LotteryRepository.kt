package com.example.sideproject.data.remote.lottery

import com.example.sideproject.data.model.ApiBaseResponse
import com.example.sideproject.data.model.Winning
import com.example.sideproject.data.remote.Service
import io.reactivex.Single

class LotteryRepository (private var service: Service) {

    fun getWinningList(page : Int) : Single<ApiBaseResponse<ArrayList<Winning>>>{
        val params = HashMap<String, String>()
        params["page"] = page.toString()
        return service.getWinningList(params)
    }
}