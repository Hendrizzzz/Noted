package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TermsAndPoliciesView {

    @Composable
    fun TermsAndPoliciesWindow(onClose: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Close button
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = onClose) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Terms and Policies content
                Text(
                    text = "Terms and Policies",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = buildTermsAndPoliciesText(),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Start,
                    color = Color.DarkGray
                )
            }
        }
    }

    private fun buildTermsAndPoliciesText(): String {
        return """
        Terms and Policies
        
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
}
