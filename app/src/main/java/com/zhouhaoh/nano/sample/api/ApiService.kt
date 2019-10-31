package com.zhouhaoh.nano.sample.api

import com.zhouhaoh.nano.sample.data.BaseResponse
import com.zhouhaoh.nano.sample.data.TestResponse
import com.zhouhaoh.nano.sample.data.TodayNews
import retrofit2.http.GET

interface ApiService {
    @GET("/api/random/data/Android/20")
    suspend fun todayNews(): BaseResponse<List<TodayNews>>

    @GET("/api/random/data/Android/20")
    suspend fun todayNewsV2(): TestResponse
}