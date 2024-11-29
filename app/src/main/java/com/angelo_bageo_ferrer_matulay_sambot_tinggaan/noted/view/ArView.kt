package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

class ARView() {
    @Composable
    fun DisplayView() {
        Surface(color = MaterialTheme.colorScheme.background) {
            ARScreen() // Calls the AR
        }
    }

}
