package com.zhouhaoh.nano.sample.data

data class TodayNews(val desc: String, val url: String) {
    override fun toString(): String {
        return desc
    }
}