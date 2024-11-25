package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view

import android.app.DatePickerDialog
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.R
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller.LandingPageController
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.BackgroundColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.window.Popup
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.ErrorMessage
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.LogoColor
import kotlinx.coroutines.delay
import java.util.Calendar


class SignUpView {

    @Preview
    @Composable
    fun DisplayView() {
        var currentScreen = remember { mutableStateOf("SignUp") }
        var showTermsAndPolicies = remember { mutableStateOf(false) }

        var email = remember { mutableStateOf(TextFieldValue("")) }
        var firstName = remember { mutableStateOf("") }
        var lastName = remember { mutableStateOf("") }
        var birthDate = remember { mutableStateOf("") }
        var password = remember { mutableStateOf("") }
        var confirmPassword = remember { mutableStateOf("") }

        var errorMessage = remember { mutableStateOf("") }

        if (showTermsAndPolicies.value) {
            AlertDialog(
                onDismissRequest = { showTermsAndPolicies.value = false },
                title = { Text("Terms and Policies") },
                text = {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(8.dp)
                    ) {
                        Text(buildTermsAndPoliciesText())
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showTermsAndPolicies.value = false },
                        colors = ButtonDefaults.buttonColors
                            (
                            containerColor = LogoColor,
                            contentColor = Color.White
                        )
                    )
                    {
                        Text("Close")
                    }
                }
            )
        }

        when (currentScreen.value) {
            "LogIn" -> LandingPageController().OnLogInClick()
            "Home" -> LandingPageController().GoToHomePage()
            else -> SignUpScreen(
                currentScreen,
                showTermsAndPolicies,
                email,
                firstName,
                lastName,
                birthDate,
                password,
                confirmPassword,
                errorMessage,
            )
        }

    }


    private fun buildTermsAndPoliciesText(): String {
        return """        
        1. Acceptance of Terms
        By accessing or using our services, you agree to be bound by these terms and conditions. If you do not agree, please refrain from using our services.

        2. Use of Services
        Our services are provided for personal and non-commercial use only. Unauthorized use, reproduction, or distribution of any content or materials is strictly prohibited.

        3. User Responsibilities
        You are responsible for maintaining the confidentiality of your account information and for any activity that occurs under your account.

        4. Prohibited Conduct
        You agree not to engage in any activities that are harmful, illegal, or disrupt the functionality of our services.

        5. Termination
        We reserve the right to terminate or suspend access to our services at any time, without notice, for any violation of these terms.

        Privacy Policy
        
        1. Information We Collect
        We may collect personal information, including your name, email address, and usage data, to provide and improve our services.

        2. Use of Information
        The information collected is used solely for the purposes of delivering and enhancing our services. We do not sell or share your information with third parties without your consent.

        3. Data Security
        We implement appropriate measures to protect your data but cannot guarantee its absolute security.

        4. Cookies
        Our services may use cookies to enhance your browsing experience. You may disable cookies in your browser settings, but this may affect service functionality.

        5. User Rights
        You have the right to access, update, or delete your personal information. Please contact us to make such requests.

        Disclaimer
        Our services are provided "as is" without warranties of any kind. We are not liable for any damages arising from the use or inability to use our services.
    """.trimIndent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SignUpScreen(
        currentScreen: MutableState<String>,
        showTermsAndPolicies: MutableState<Boolean>,
        email: MutableState<TextFieldValue>,
        firstName: MutableState<String>,
        lastName: MutableState<String>,
        birthDate: MutableState<String>,
        password: MutableState<String>,
        confirmPassword: MutableState<String>,
        errorMessage: MutableState<String>
    ) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        val passwordVisible = remember { mutableStateOf(false) }
        val confirmPasswordVisible = remember { mutableStateOf(false) }

        val passwordError = password.value.length < 8
        val confirmPasswordError = confirmPassword.value != password.value

        val isChecked = remember { mutableStateOf(false) }
        var isErrorVisible by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            // Logo Image
            Image(
                painter = painterResource(id = R.drawable.noted_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = screenHeight * 0.01f)
            )

            // Scrollable form section
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding
                        (
                        screenWidth * 0.05f + 16.dp,
                        (screenHeight * 0.01f) + 166.dp,
                        screenWidth * 0.05f + 16.dp,
                        16.dp
                        )
                    .align(Alignment.Center)
            ) {
                val paddingStart = (screenWidth - 600.dp) / 2
                val finalPadding = if (paddingStart < 0.dp) 0.dp else paddingStart

                // Email Label
                Text(
                    text = "Email",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = finalPadding, top = 8.dp)
                )

                val emailError = !Patterns.EMAIL_ADDRESS.matcher(email.value.text).matches()

                // Email TextField
                TextField(
                    modifier = Modifier
                        .width(600.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    value = email.value,
                    onValueChange = { email.value = it },
                    placeholder = { Text(" Enter Email", fontSize = 16.sp) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    isError = emailError,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.medium,
                    textStyle = TextStyle(fontSize = 16.sp)
                )

                if (emailError)
                    Text(text = "Invalid email format",
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = finalPadding))

                // First Name Label
                Text(
                    text = "First Name",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .padding(start = finalPadding, top = 8.dp)
                )

                // First name TextField
                TextField(
                    modifier = Modifier
                        .width(600.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    value = firstName.value,
                    onValueChange = { firstName.value = it },
                    placeholder = { Text("Enter First Name", fontSize = 16.sp) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.medium,
                    textStyle = TextStyle(fontSize = 16.sp)
                )

                // Last Name Label
                Text(
                    text = "Last Name",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .padding(start = finalPadding, top = 8.dp)
                )

                // Last Name TextField
                TextField(
                    modifier = Modifier
                        .width(600.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    value = lastName.value,
                    onValueChange = { lastName.value = it },
                    placeholder = { Text("Enter Last Name", fontSize = 16.sp) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.medium,
                    textStyle = TextStyle(fontSize = 16.sp)
                )

                // Birthdate Label
                Text(
                    text = "Birthdate",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .padding(start = finalPadding, top = 8.dp)
                )

                // Birthdate TextField
                TextField(
                    modifier = Modifier
                        .width(600.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    value = birthDate.value,
                    onValueChange = { birthDate.value = it },
                    placeholder = { Text("DD/MM/YYYY", fontSize = 16.sp) },
                    readOnly = true,
                    trailingIcon = {
                        val context = LocalContext.current
                        val currentDate = remember { Calendar.getInstance() }

                        IconButton(onClick = {
                            val datePickerDialog = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val date = "$dayOfMonth/${month + 1}/$year"
                                    birthDate.value = date
                                },
                                currentDate.get(Calendar.YEAR),
                                currentDate.get(Calendar.MONTH),
                                currentDate.get(Calendar.DAY_OF_MONTH)
                            )
                            datePickerDialog.show()
                        }, modifier = Modifier.padding(0.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar),
                                contentDescription = "Calendar",
                                modifier = Modifier
                                    .padding(0.dp)
                                    .size(20.dp)
                            )
                        }
                    },
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
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .padding(start = finalPadding, top = 8.dp)
                )

                // Password TextField
                TextField(
                    modifier = Modifier
                        .width(600.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    value = password.value,
                    onValueChange = { password.value = it },
                    placeholder = { Text("••••••••", fontSize = 16.sp) },
                    visualTransformation =
                        if (passwordVisible.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible.value = !passwordVisible.value }
                        ) {
                            Icon(
                                painter = if (passwordVisible.value) {
                                    painterResource(id = R.drawable.eye_password_show)
                                } else {
                                    painterResource(id = R.drawable.eye_password_hide)
                                },
                                contentDescription =
                                    if (passwordVisible.value) "Hide Password"
                                    else "Show Password",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.medium,
                    textStyle = TextStyle(fontSize = 16.sp)
                )

                // Password Error Message
                if (passwordError) {
                    Text(
                        text = "Password must be at least 8 characters long",
                        color = Color.Red,
                        modifier = Modifier.padding(start = finalPadding, top = 8.dp)
                    )
                }

                // Confirm Password Label
                Text(
                    text = "Confirm Password",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .padding(start = finalPadding, top = 8.dp)
                )

                // Confirm Password TextField with Show/Hide Toggle inside the field
                TextField(
                    modifier = Modifier
                        .width(600.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    value = confirmPassword.value,
                    onValueChange = { confirmPassword.value = it },
                    placeholder = { Text("••••••••", fontSize = 16.sp)},
                    visualTransformation =
                        if (confirmPasswordVisible.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmPasswordVisible.value = !confirmPasswordVisible.value })
                        {
                            Icon(
                                painter = if (confirmPasswordVisible.value) {
                                    painterResource(id = R.drawable.eye_password_show)
                                } else {
                                    painterResource(id = R.drawable.eye_password_hide)
                                },
                                contentDescription =
                                    if (confirmPasswordVisible.value) "Hide Password"
                                    else "Show Password",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.medium,
                    textStyle = TextStyle(fontSize = 16.sp)
                )

                // Confirm Password Error Message
                if (confirmPasswordError) {
                    Text(
                        text = "Passwords do not match",
                        color = Color.Red,
                        modifier = Modifier.
                        padding(start = finalPadding, top = 8.dp)
                    )
                }


                // Agreement Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                        .height(15.dp)
                        .padding(start = finalPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .scale(0.5f)
                    ) {
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = { isChecked.value = it },
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center)
                        )
                    }

                    // Clickable Text for Terms and Conditions
                    Text(
                        text = "I AGREE TO TERMS & POLICIES",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            color = LogoColor,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                showTermsAndPolicies.value = true
                            }
                    )
                }


                Button(
                    onClick = {
                        if (emailError) {
                            errorMessage.value = "Invalid email format"
                            isErrorVisible = true
                        } else if (firstName.value.isEmpty() || lastName.value.isEmpty()) {
                            errorMessage.value = "Please fill in all required fields"
                            isErrorVisible = true
                        } else if (birthDate.value.isEmpty()) {
                            errorMessage.value = "Please select a birthdate"
                            isErrorVisible = true
                        } else if (passwordError) {
                            errorMessage.value = "Password must be at least 8 characters long"
                            isErrorVisible = true
                        } else if(confirmPasswordError) {
                            errorMessage.value = "Passwords do not match"
                            isErrorVisible = true
                        } else if (!isChecked.value) {
                            errorMessage.value = "You must agree to the terms and conditions"
                            isErrorVisible = true
                        } else {
                            val isCredentialsValid = LandingPageController().
                                validateCredentials (
                                    email.value.text,
                                    firstName.value,
                                    lastName.value,
                                    birthDate.value,
                                    password.value
                                )
                            if (isCredentialsValid) {
                                currentScreen.value = "Home"
                                isErrorVisible = false
                            } else {
                                errorMessage.value = "Account already exists"
                                isErrorVisible = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LogoColor
                    ),
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Create Account",
                        color = Color.White
                    )
                }


                if (isErrorVisible)
                    ErrorPopUp(errorMessage)

                // Automatically hide the error message after 3 seconds
                LaunchedEffect(isErrorVisible) {
                    delay(3000)
                    isErrorVisible = false
                }


                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(
                        onClick = {
                            currentScreen.value = "LogIn"
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
                            text = "Log In Instead",
                            color = LogoColor,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }

            }
        }
    }

    @Composable
    fun ErrorPopUp(errorMessage: MutableState<String>) {
        Popup(
            alignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .width(600.dp)
                    .background(ErrorMessage, shape = RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = errorMessage.value,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}