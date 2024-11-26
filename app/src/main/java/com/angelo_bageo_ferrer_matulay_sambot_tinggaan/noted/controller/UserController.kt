package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller

import android.content.Context

class UserController(private val authenticationController: AuthenticationController) {

    fun logOut(context: Context) {
        authenticationController.logOut(context)
    }
}