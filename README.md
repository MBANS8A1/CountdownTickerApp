# Countdown Timer Compose Application

## Description of the timer app:

This application will start from a start time specified in the code (default: 30 seconds) and will count down to 0 (zero), when the "Start" button is clicked. You can use this application for timing how fast you can 
complete certain challenges; for example:
 - speaking only one language for 30 seconds
 - spelling 10 difficult words quickly
 - reciting a poem quickly
 - doing a set number of burpees or other exercise quickly for intensive exercise
 - creating the longest words from a random selection of letters (e.g 10 letters containing a mixture of vowels and consonants.


## Technical Description:

The application makes use of techniques in Jetpack Compose to create animations, transitions and with the help of the Canvas API to draw an animated arc to simulate counting down in a smooth way. Furthermore, the control of time uses state control techniques like MutableStateFlow, StateFlow (for emitting the values from the ViewModel to be used as states in the Timer composable function) and the CountDownTimer API, which has had its methods overriden. The events are passed from the composable caller (Timer composable in the MainActivity), which is then fed to the ViewModel, so it can then update the state as needed.

 <img src="https://github.com/MBANS8A1/CountdownTickerApp/blob/48b99851c39f73a9674d12f2f1aeabc72746d33c/app/src/main/res/drawable/timer_image1.png" alt="Start time" width="250" height="490">

 <img src="https://github.com/MBANS8A1/CountdownTickerApp/blob/48b99851c39f73a9674d12f2f1aeabc72746d33c/app/src/main/res/drawable/timer_image2.png" alt="Countdown" width="250" height="490">


