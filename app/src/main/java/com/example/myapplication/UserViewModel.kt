package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository(MockApi())
//    val usersFlow = userRepository.getUsersFromStream("")

    init {
        exhibitUsersFromFlow()
    }

    fun exhibitUsersFromFlow() {
        viewModelScope.launch {
            val flow = userRepository.getUsersFromStream("")
                .flatMapLatest {
                    Log.e("flatMapLatest", "回傳一個 Flow，直接把前一個還沒處理完的 flow 取消")
                    flow { emit(it) }
                }

            flow.collect {
                Log.e("collect", "針對每個 collect 回來的值做處理，在 View Model 中可用 launchIn() 取代")
            }
            flow.collectLatest {
                Log.e("collectLatest", "處理最新的 collect，直接把前一個還沒處理完的 collect 取消")
            }
        }
    }

    fun exhibitUsersFromSingle() {
        viewModelScope.launch {
            val users = userRepository.getUsersFromSingle()
        }
    }
}
