package com.zhouhaoh.nano.sample.mvvm

import com.zhouhaoh.nano.mvvm.BaseRepository
import com.zhouhaoh.nano.sample.api.ApiService
import com.zhouhaoh.nano.sample.base.withIOContext

class SampleRepository(private val apiService: ApiService) : BaseRepository() {
    suspend fun toadyNews() = withIOContext {
        apiService.todayNews()
    }
}