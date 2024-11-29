package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.R
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller.AuthenticationController
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.SelectedColor
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.AccountView
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.ARView
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.MapView

class MainController(private val authenticationController: AuthenticationController) {
    private val arView = ARView()
    private val mapView = MapView()
    private val accountView = AccountView()

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        var selectedButton by remember { mutableStateOf("Ar") }

        Scaffold(
            bottomBar = {
                BottomNavigationBar(selectedButton) { buttonLabel ->
                    selectedButton = buttonLabel

                    when (buttonLabel) {
                        "Ar" -> navController.navigate("ar")
                        "Accounts" -> navController.navigate("accounts")
                        "Maps" -> navController.navigate("maps")
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "ar",
                Modifier.padding(innerPadding)
            ) {
                composable("ar") { arView.DisplayView() }
                composable("accounts") { accountView.AccountScreen(authenticationController) }
                composable("maps") { mapView.MapsScreen() }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(selectedButton: String, onButtonClick: (String) -> Unit) {
        NavigationBar(
            modifier = Modifier
                .height(55.dp)
                .drawBehind {
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 4f
                    )
                }
        ) {
            NavigationBarItem(
                selected = false,
                onClick = { onButtonClick("Maps") },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.map),
                        contentDescription = "Maps",
                        tint = if (selectedButton == "Maps") SelectedColor else Color.Black
                    )
                },
                modifier = Modifier.padding(7.5.dp)
            )
            NavigationBarItem(
                selected = false,
                onClick = { onButtonClick("Ar") },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_notes),
                        contentDescription = "AR",
                        tint = if (selectedButton == "Ar") SelectedColor else Color.Black
                    )
                },
                modifier = Modifier.padding(7.5.dp)
            )
            NavigationBarItem(
                selected = false,
                onClick = { onButtonClick("Accounts") },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_account),
                        contentDescription = "Accounts",
                        tint = if (selectedButton == "Accounts") SelectedColor else Color.Black
                    )
                },
                modifier = Modifier.padding(7.5.dp)
            )
        }
    }
}
