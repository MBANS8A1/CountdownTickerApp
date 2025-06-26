package com.example.countdowntickerapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel:ViewModel(){
    companion object{
        //30 seconds
        const val totalTime = 30 * 1000L
        //1 second
        const val interval =  1000L
    }
    private val _currentTime : MutableStateFlow<Long> = MutableStateFlow(totalTime)
    val currentTime:StateFlow<Long>
        get() = _currentTime

    private val _isTimerRunning: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTimerRunning:StateFlow<Boolean>
        get() = _isTimerRunning


}