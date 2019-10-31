package com.delicloud.common.utils

import com.zhouhaoh.nano.tools.otherwise
import com.zhouhaoh.nano.tools.yes
import java.util.*

fun getYear(): String {
    return Calendar.getInstance().get(Calendar.YEAR).toString().substring(2..3)
}

fun getMonth(): String {
    val month = (Calendar.getInstance().get(Calendar.MONTH) + 1).toString()
    return (month.length == 1).yes {
        "0$month"
    } otherwise {
        month
    }
}

fun getDay(): String {
    val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
    return (day.length == 1).yes {
        "0$day"
    } otherwise {
        day
    }
}

fun getHour(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
    return (hour.length == 1).yes {
        "0$hour"
    } otherwise {
        hour
    }
}

fun getMinute(): String {
    val minute = Calendar.getInstance().get(Calendar.MINUTE).toString()
    return (minute.length == 1).yes {
        "0$minute"
    } otherwise {
        minute
    }
}