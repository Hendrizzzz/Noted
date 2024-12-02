package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller.AuthenticationController
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.model.User
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.SplashScreen


/**
 * The entry point of the application doug
 */
class Noted : ComponentActivity() {
    private val user = User()
    private val authenticationController = AuthenticationController(user)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SplashScreen(authenticationController).DisplayView()
        }
    }
}
