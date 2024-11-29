package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller.AuthenticationController
import androidx.compose.ui.platform.LocalContext
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.main.Noted

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.R
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.AccountHeaderColor

class AccountView {

    val customFontFamily = FontFamily(
        Font(R.font.inter)
    )

    @Preview
    @Composable
    fun AccountScreen(authenticationController: AuthenticationController = AuthenticationController()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 32.dp,
                    shape = RectangleShape,
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .background(AccountHeaderColor) // Apply background after shadow for clarity
                .drawBehind {
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 4f
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Space between elements
        ) {
            // App Icon on the left
            IconButton(onClick = { /* Handle app icon click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.noted_logo),
                    contentDescription = "App Icon"
                )
            }

            // Center Text ("Account")
            Text(
                text = "Account",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.W900,   // Optional: Apply custom weight
                ),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 26.sp
            )

            // Settings Icon on the right
            IconButton(onClick = { /* Handle settings icon click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_settings), // Replace with your settings icon resource
                    contentDescription = "Settings Icon"
                )
            }
        }

            val context = LocalContext.current
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    modifier = Modifier.align(Alignment.BottomCenter),
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
