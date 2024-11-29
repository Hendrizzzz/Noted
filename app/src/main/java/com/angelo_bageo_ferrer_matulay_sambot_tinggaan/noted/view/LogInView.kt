package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.R
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.BackgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.zIndex
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller.AuthenticationController
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.ErrorMessage
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.LogoColor
import kotlinx.coroutines.delay



class LogInView(private val authenticationController: AuthenticationController) {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf("")
    var isErrorVisible by mutableStateOf(false)

    @Composable
    fun DisplayView() {
        val currentScreen = remember { mutableStateOf("LogIn") }

        when (currentScreen.value) {
            "SignUp" -> authenticationController.OnSignUpClick()
            "Home" -> authenticationController.GoToHomePage()
            else -> LogInScreen(currentScreen)
        }
    }

    @Composable
    fun LogInScreen(currentScreen: MutableState<String>) {
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {

            // logo
            Image(
                painter = painterResource(id = R.drawable.noted_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = screenHeight * 0.1f)
                    .zIndex(2f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    screenWidth * 0.05f,
                    screenHeight * 0.35f,
                    screenWidth * 0.05f,
                    screenHeight * 0.28f
                )
        ) {
            EmailAndPasswordTextFields()
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding
                    (
                    screenWidth * 0.05f,
                    screenHeight * 0.6f,
                    screenWidth * 0.05f,
                    screenHeight * 0.23f
                )
        ) {
            // Log In Button
            Button(
                onClick = {
                    try {
                    authenticationController.validateLogin(email, password) { isSuccess ->
                        if (isSuccess)
                            currentScreen.value = "Home"
                        else {
                            errorMessage = "Invalid email or password. Please try again."
                            isErrorVisible = true
                        }
                    }
                        } catch (exception : IllegalArgumentException) {
                            errorMessage = exception.message.toString()
                            isErrorVisible = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = LogoColor),
                modifier = Modifier.width(250.dp)
                    .align(Alignment.Center),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Log In",
                    color = Color.White,
                )
            }

            // Forgot Password Link
            Text(
                text = "Forgot Password?",
                style = TextStyle
                    (
                    color = LogoColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = screenHeight * 0.1f)
                    .clickable {
                        println("Forgot Password clicked!")
                    }
            )
        }


        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    currentScreen.value = "SignUp"
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(135.dp)
                    .padding(horizontal = 10.dp, vertical = 40.dp)
                    .border(1.dp, LogoColor, RoundedCornerShape(32.dp))
                    .align(Alignment.BottomCenter),

                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Sign Up Instead",
                    color = LogoColor,
                    textDecoration = TextDecoration.Underline
                )
            }
        }

        if (isErrorVisible) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(ErrorMessage, shape = RoundedCornerShape(20.dp))
                        .padding(16.dp)
                        .width(600.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = errorMessage,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        LaunchedEffect(isErrorVisible) {
            delay(3000)
            isErrorVisible = false
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EmailAndPasswordTextFields() {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        var passwordVisible by remember { mutableStateOf(false) }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            var calcTextPadding = (screenWidth - 600.dp) / 2 - 64.dp
            if (calcTextPadding < 0.dp)
                calcTextPadding = 0.dp

            // Email Label
            Text(
                text = "Email",
                style = TextStyle
                    (
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(calcTextPadding, 0.dp, 0.dp, 8.dp)
            )

            // Email TextField
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter Email or Username", fontSize = 16.sp) },
                modifier = Modifier
                    .width(600.dp)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.medium,
                textStyle = TextStyle(fontSize = 16.sp)
            )

            // Password Label
            Text(
                text = "Password",
                style = TextStyle
                    (
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(calcTextPadding, 0.dp, 0.dp, 8.dp)
            )

            // Password TextField
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("••••••••", fontSize = 16.sp) },
                visualTransformation =
                    if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                modifier = Modifier
                    .width(600.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.medium,
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id =
                                if (passwordVisible) R.drawable.eye_password_show
                                else R.drawable.eye_password_hide),
                            contentDescription = "Toggle Password Visibility",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }
    }

}
