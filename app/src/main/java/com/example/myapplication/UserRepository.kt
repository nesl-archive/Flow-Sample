package com.example.myapplication

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class UserRepository(
    private val api: MockApi,
) {
    fun getUsersFromStream(query: String): Flow<List<String>> {
        return api.getAllAsStream()
            .onStart {
                Log.d("onStart", "不回傳，開始前先對 flow 做一些事情，不受擺放順序影響")
            }
            .map {
                Log.d("map", "會更改資料型態，可隨意擺放")
                it
            }
            .onEach {
                Log.d("onEach", "不回傳，針對每一個資料做處理，可能會被 map、flatMapConcat、flatMapMerge 等影響")
            }
            .flatMapConcat {
                // https://blog.csdn.net/unicorn97/article/details/105209834
                Log.e("flatMapConcat", "回傳一個 Flow，通常用於將 flow 串聯來解決 callback hell")
                flow { emit(it) }
            }
            .flatMapMerge {
                // https://blog.csdn.net/unicorn97/article/details/105209834
                Log.e("flatMapMerge", "回傳一個 Flow，通常用於將 flow 並聯來解決並發需求 ")
                flow { emit(it) }
            }
            .onCompletion {
                Log.d("onCompletion", "不回傳，結束後對 flow 做一些事情")
            }
            .catch {
                Log.e("catch", "整個 flow 出現了錯誤")
                it.printStackTrace()
            }
    }

    suspend fun getUsersFromSingle(): List<String> = withContext(Dispatchers.IO){ api.getAllAsSingle("") }
}