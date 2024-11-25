package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view

import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller.LandingPageController
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.BackgroundColor
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.LogoColor
import kotlinx.coroutines.delay


class SplashScreen : ComponentActivity() {
    val landingPageController = LandingPageController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShowSplashScreen();

        }


    }

    @Preview
    @Composable
    fun ShowSplashScreen() {
        val isLoading = remember { mutableStateOf(true) }
        val buttonYOffset = remember { mutableStateOf(200.dp) }

        val currentScreen = remember { mutableStateOf("Splash") }

        LaunchedEffect(Unit) {
            delay(2000)
            isLoading.value = false
        }


        when (currentScreen.value) {
            "LogIn" -> landingPageController.OnLogInClick()
            "SignUp" -> landingPageController.OnSignUpClick()
            else -> {
                // Show the splash screen initially
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundColor)
                ) {
                    ShowLogo()
                    ShowLoadingIconAnimation(isLoading)
                    ShowButtons(isLoading, currentScreen, buttonYOffset)
                }
            }
        }
    }


    @Composable
    fun ShowButtons(
        isLoading: MutableState<Boolean>,
        currentScreen: MutableState<String>,
        buttonYOffset: MutableState<Dp>
    ) {
        // Buttons (Slide up after 2 seconds)
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = !isLoading.value,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 1000)
                ),
                exit = fadeOut(tween(durationMillis = 300)),
                modifier = Modifier.offset(0.dp, -(100.dp)).align(Alignment.Center)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = buttonYOffset.value)
                ) {
                    Button(
                        onClick = {
                            currentScreen.value = "LogIn"
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LogoColor
                        ),
                        modifier = Modifier.width(200.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Log In",
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            currentScreen.value = "SignUp"
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.width(200.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Sign Up",
                            color = LogoColor,
                        )
                    }

                }
            }
        }
    }


    @Composable
    fun ShowLogo() {
        // Logo
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.noted_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.Center)
                    .offset(y = (-75).dp)
            )
        }
    }

    @Composable
    fun ShowLoadingIconAnimation(isLoading: MutableState<Boolean>) {
        // Loading icon
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = isLoading.value,
                enter = fadeIn(tween(durationMillis = 0)),
                exit = fadeOut(tween(durationMillis = 700)),
                modifier = Modifier.align(Alignment.Center)
                    .offset(0.dp, 100.dp)
            ) {
                CircularProgressIndicator(
                    color = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }


}