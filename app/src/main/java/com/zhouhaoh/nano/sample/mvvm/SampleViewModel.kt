package com.zhouhaoh.nano.sample.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zhouhaoh.nano.mvvm.BaseViewModel
import timber.log.Timber

class SampleViewModel(private val sampleRepository: SampleRepository) : BaseViewModel() {
    private val _toadyNewsDesc = MutableLiveData<String>()
    val toadyNewsDesc: LiveData<String>
        get() = _toadyNewsDesc

    init {
        _toadyNewsDesc.value = "Hello Nano!!!"
    }

    fun toadyNews() {
        launch {
            val toadyNews = sampleRepository.toadyNews()
            Timber.i("--toadyNews:%s---", toadyNews)
            _toadyNewsDesc.value = "toadyNews size : $toadyNews"
        }
    }
}