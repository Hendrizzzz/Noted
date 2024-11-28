package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller.AuthenticationController
import androidx.compose.ui.platform.LocalContext
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.main.Noted

class AccountView {
    @Composable
    fun AccountScreen(authenticationController: AuthenticationController) {
        val context = LocalContext.current
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    authenticationController.logOut()
                    context.startActivity(Intent(context, Noted::class.java))
                }
            ) {
                Text(text = "Sign Out")
            }
        }
    }
}
