package com.example.myapplication

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockApi {
    fun get(key: Int): Flow<String> {
        return flow {
            emit("from get api")
        }
    }

    fun getAllAsStream(): Flow<List<String>> {
        return flow {
            emit(listOf("result1 from getAll api", "result2 from getAll api", "result3 from getAll api"))
        }
    }

    suspend fun getAllAsSingle(query: String): List<String> {
        return listOf("result1 from getAll api")
    }
}
