package com.zhouhaoh.nano.sample.mvvm

import com.zhouhaoh.nano.mvvm.BaseRepository
import com.zhouhaoh.nano.sample.api.ApiService
import com.zhouhaoh.nano.sample.base.withIOContext
import com.zhouhaoh.nano.sample.base.withIOV2Context

class SampleRepository(private val apiService: ApiService) : BaseRepository() {
    suspend fun toadyNews() = withIOContext {
        apiService.todayNews()
    }

    suspend fun todayNewsV2() = withIOV2Context {
        apiService.todayNewsV2()
    }
}