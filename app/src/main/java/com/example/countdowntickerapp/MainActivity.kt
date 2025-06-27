package com.example.countdowntickerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.countdowntickerapp.ui.theme.CountdownTickerAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountdownTickerAppTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CountDownTickerApp()
                }
            }
        }
    }
    @Composable
    fun CountDownTickerApp(){
        val currentTime by viewModel.currentTime.collectAsState()
        val isRunning by viewModel.isTimerRunning.collectAsState()
        Timer(currentTime=currentTime,
            isRunning=isRunning,
            onStart = {
                viewModel.startTimer()
            },
            onRestart = {
                viewModel.restartTimer()
            }
        )
    }
}

