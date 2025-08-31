package com.example.countdowntickerapp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.countdowntickerapp.ui.theme.blue200
import com.example.countdowntickerapp.ui.theme.blue400
import com.example.countdowntickerapp.ui.theme.blue500
import com.example.countdowntickerapp.ui.theme.card

const val TIMER_RADIUS = 300f

@Composable
private fun DialogBox(
    onDismissRequest: () -> Unit,
    onUpdateTimerState: () -> Unit,
    dialogText: String,
    timerState: TimerState

)
{

    Card(
        modifier = Modifier
            .height(200.dp)
            .width(400.dp)
            .padding(15.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

       Column(
           modifier = Modifier
               .fillMaxSize(),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Text(text=dialogText,
               modifier = Modifier.padding(10.dp))

           OutlinedTextField(
               value = timerState.totalTime,
               onValueChange = {
                   if(it.isEmpty() || it.matches(timerState.numberPattern)){
                       timerState.totalTime =it
                   }
                               },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
           )
           Row(
               modifier = Modifier
                   .fillMaxWidth(),
               horizontalArrangement = Arrangement.Center,
           ) {
               TextButton(
                   onClick = { onDismissRequest() },
                   modifier = Modifier.padding(8.dp),
               ) {
                   Text("Dismiss")
               }
               TextButton(
                   onClick = { onUpdateTimerState() },
                   modifier = Modifier.padding(8.dp),
               ) {
                   Text("Confirm")
               }
           }
       }

    }

}



@Composable
fun Timer(currentTime: Long,
          isRunning:Boolean,
          onStart: () -> Unit,
          onRestart: () -> Unit,
          timerState: TimerState,
          onDismissRequest: () -> Unit,
          onUpdateTimerState: () -> Unit,
          dialogText: String,
          showDialogValue: Boolean
) {


    val transition = updateTransition(targetState = currentTime, label = null)

    val tran by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 1000,easing= FastOutLinearInEasing)},
            label = ""
    ){ timeLeft ->
        if(timeLeft < 0 ){
            360f
        }
        else{
            360f - ((360f /timerState.totalTime.toLong()) * (timerState.totalTime.toLong()-timeLeft))
        }
    }

    val progress by animateFloatAsState(
        targetValue = if(isRunning) tran else 0f

    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Row(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp)
        ){
            Button(
                enabled = !isRunning,
                onClick = {
                    !showDialogValue
                }

            ) {
                Text("Choose start time")
            }
        }

        Row(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp)) {
            Button(onClick = onStart) {
                Text(text = "Start")
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(onClick = onRestart) {
                Text(text = "Restart")
            }
        }

        if(showDialogValue){
            DialogBox(
                timerState = timerState,
                onDismissRequest = onDismissRequest,
                onUpdateTimerState = onUpdateTimerState,
                dialogText = dialogText
            )
        }

        CountDownTickerProgressIndicator(progress,currentTime) //moved within the bounding Box
    }

}

@Composable
fun CircularIndicator(progress: Float) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        val stroke = with(LocalDensity.current){
            Stroke(
                width = 30.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
        ) {
            inset(
                horizontal = size.width/2 - TIMER_RADIUS,
                vertical = size.height/2 - TIMER_RADIUS
            ){
                //use Brush for the gradient
                val gradient = Brush.linearGradient(
                    listOf(blue500, blue200, blue400)
                )
                drawBackGround(card)
                drawProgressIndicator(
                    brush = gradient,
                    progress,
                    stroke
                )
            }
        }
    }
}

fun DrawScope.drawBackGround(
    color:Color
) {
    drawCircle(
        color = color,
        radius = TIMER_RADIUS,
        center = center
    )
}

fun DrawScope.drawProgressIndicator(
    brush:Brush,
    progress:Float,
    stroke:Stroke
){
    //using the size property of the Canvas
    val innerRadius = (size.minDimension -stroke.width)/2
    //get the half size of the drawing area
    val halfSize = size /2.0f
    val topLeft = Offset(
        x = halfSize.width - innerRadius,
        y= halfSize.height - innerRadius

    )
    //size of arc's bounding box uses innerRadius as we take into account for stroke width
    val size = Size(innerRadius*2,innerRadius*2)
    drawArc(
        brush = brush,
        startAngle =270f,
        sweepAngle = progress,
        useCenter = false,
        topLeft = topLeft,
        size = size,
        style = stroke,
        blendMode = BlendMode.Src
    )

}

@Composable
fun CountDownTickerProgressIndicator(progress:Float,currentTime: Long) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ){
        CircularIndicator(progress = progress)
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