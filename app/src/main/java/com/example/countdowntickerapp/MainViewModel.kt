package com.example.countdowntickerapp

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel:ViewModel(){
//    var totalTime = 30 * 1000L
//    var totalTimeState = mutableLongStateOf(30*1000L)

      var timerState by mutableStateOf(TimerState())


    //30 seconds
        //1 second
      companion object {
            const val INTERVAL = 1000L

        }
    private val _currentTime : MutableStateFlow<Long> = MutableStateFlow(timerState.totalTime.toLong()*1000)
    val currentTime: StateFlow<Long>
        get() = _currentTime

    private val _isTimerRunning: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTimerRunning:StateFlow<Boolean>
        get() = _isTimerRunning

    private val timer: CountDownTimer = object : CountDownTimer(timerState.totalTime.toLong()*1000,INTERVAL){
        override fun onTick(millisUntilFinished: Long) {
            _currentTime.value =  millisUntilFinished
        }

        override fun onFinish() {
            _currentTime.value = timerState.totalTime.toLong()*1000
            _isTimerRunning.value = false
        }

    }


    fun startTimer(){
        if(isTimerRunning.value){
            resetTimer()
        }
        timer.start()
        _isTimerRunning.value = true

    }

    fun restartTimer(){
        if(_isTimerRunning.value){
            resetTimer()
        }

    }

    private fun resetTimer(){
        timer.cancel()
        _currentTime.value = timerState.totalTime.toLong()*1000
        _isTimerRunning.value = false

    }

    override fun onCleared() {
        super.onCleared()
        restartTimer()
    }

}

data class TimerState(
    var totalTime: String = "",
    var textInput:String = "",
    val numberPattern: Regex = Regex("^\\d+\$")
)