package com.example.countdowntickerapp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Timer(currentTime: Long,
          isRunning:Boolean,
          onStart: () -> Unit,
          onRestart: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(onClick = onStart) {
                Text(text = "Start")
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(onClick = onRestart) {
                Text(text = "Restart")
            }
        }

    }
}

@Composable
fun CountDownTickerProgressIndicator(progress:Float,currentTime: Long) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()
    ){
        AnimatedContent(targetState = currentTime,
            transitionSpec = {
                if(targetState > initialState){
                    fadeIn() + slideInVertically(
                        animationSpec = spring(),
                        initialOffsetY = {fullHeight -> fullHeight }) togetherWith(
                            fadeOut(animationSpec = spring())
                            )

                }else{
                    fadeIn() + slideInVertically(
                        animationSpec = spring(),
                        initialOffsetY = {fullHeight -> fullHeight }) togetherWith(
                            fadeOut(animationSpec = spring())
                            )
                }.using(SizeTransform(clip = false))
            },
        ) {time ->
            Text(text= getFormattedTime(time),
                style =  MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

        }

    }
}