package com.zhouhaoh.nano.tools

import com.google.gson.Gson
import com.tencent.mmkv.MMKV

/**
 * MMKV  扩展
 * 用于数据存储
 * Created by zhouhaoh on 2019/06/28.
 */
val kv: MMKV = MMKV.defaultMMKV()

inline fun <reified T : Any> MMKV.decode(key: String): T {
    //Timber.d("T::class,${T::class},$key,${T::class.simpleName}")
    return when (T::class) {
        Boolean::class -> decodeBool(key, false) as T
        Int::class -> decodeInt(key, 0) as T
        Long::class -> decodeLong(key, 0L) as T
        Float::class -> decodeFloat(key, 0F) as T
        Double::class -> decodeDouble(key, 0.00) as T
        String::class -> decodeString(key, "") as T
        else -> decodeFromJson(key)
    }
}


fun MMKV.encodeToJson(key: String, value: Any) = encode(key, Gson().toJson(value))


inline fun <reified T : Any> MMKV.decodeFromJson(key: String) = Gson().fromJson<T>(decodeString(key), T::class.java)

