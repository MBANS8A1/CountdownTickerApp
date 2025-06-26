package com.example.countdowntickerapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel:ViewModel(){


    companion object{
        //30 seconds
        const val totalTime = 30 * 1000L
        //1 second
        const val interval =  1000L
    }

}