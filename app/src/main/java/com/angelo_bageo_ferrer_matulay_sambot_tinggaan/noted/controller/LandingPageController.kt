package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.main.MainController
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.LogInView
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.SignUpView

class LandingPageController {
    private val logInView = LogInView()
    private val signUpView = SignUpView()
    private val mainController = MainController()


    @Composable
    fun OnLogInClick() {
        logInView.DisplayView()
    }

    @Composable
    fun OnSignUpClick() {
        signUpView.DisplayView()
    }

    fun validateLogin(email: String, password: String): Boolean {
        return !(email.isEmpty() || password.isEmpty())
    }

    @Composable
    fun GoToHomePage() {
        mainController.AppNavigation()
    }


    fun validateCredentials(email: String, firstName: String, lastName: String, birthDate: String, password: String): Boolean {
        return true
    }

}