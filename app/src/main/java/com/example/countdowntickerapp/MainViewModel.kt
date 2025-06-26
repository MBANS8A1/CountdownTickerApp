package com.example.countdowntickerapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel:ViewModel(){
    private val _currentTime : MutableStateFlow<Long> = MutableStateFlow(totalTime)
    val currentTime:StateFlow<Long>
        get() = _currentTime
    companion object{
        //30 seconds
        const val totalTime = 30 * 1000L
        //1 second
        const val interval =  1000L
    }

}