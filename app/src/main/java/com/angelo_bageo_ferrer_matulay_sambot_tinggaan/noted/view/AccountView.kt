package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view

import android.content.Intent
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.R
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.model.User
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.AccountHeaderColor
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.LogoColor
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.NotesColor
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.ui.theme.YellowColor

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class AccountView(private var user : User, authenticationController: AuthenticationController) {

    val customFontFamily = FontFamily(
        Font(R.font.inter)
    )


    @Composable
    fun AccountScreen(authenticationController: AuthenticationController = AuthenticationController(user)) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 32.dp,
                    shape = RectangleShape,
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .background(AccountHeaderColor)
                .drawBehind {
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 4f
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /*  */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.noted_logo),
                    contentDescription = "App Icon"
                )
            }

            Text(
                text = "Account",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.W900,
                ),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 26.sp
            )

            // Settings Icon on the right
            IconButton(onClick = { /* Handle settings icon click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_settings),
                    contentDescription = "Settings Icon"
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize()
            .padding(horizontal = 10.dp)
        ) {

            val context = LocalContext.current

            val userData = remember { mutableStateOf<User?>(null) }


            Spacer(modifier = Modifier.height(50.dp))

            // User Details Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // User Icon
                Icon(
                    painter = painterResource(id = R.drawable.yellow_user_icon),
                    contentDescription = "User Icon",
                    modifier = Modifier
                        .size(60.dp),
                    YellowColor
                )

                // User details (Last Name, First Name)
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f), // Take remaining space
                    verticalArrangement = Arrangement.Center
                ) {
                        Text(
                            text = user.getLastName().toUpperCase() + ",",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.W500,
                            ),
                            fontSize = 28.sp,
                            color = YellowColor
                        )
                        Text(
                            text = user.getFirstName(),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.W300,
                            ),
                            fontSize = 18.sp
                        )

                    // Edit Profile Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Edit Profile",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 10.sp,
                            modifier = Modifier.clickable { /* Handle Edit Profile click */ }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.edit_profile_icon),
                            contentDescription = "Edit Profile Icon",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }


            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(NotesColor)
                    .height(175.dp)
            )
            {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                ) {
                    Text(
                        text = "My Notes",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.W500,
                        ),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 22.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(-(10).dp)
                        ) {
                            Text(
                                text = user.getTotalNoted().toString(),
                                fontSize = 50.sp,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = customFontFamily,
                                ),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = "Noted!",
                                fontSize = 20.sp,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                    .padding(bottom = 20.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .background(LogoColor)
                                    .padding(horizontal = 10.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {

                                    }
                            ) {
                                Text(
                                    text = "View ",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = customFontFamily,
                                    ),
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.view_icon),
                                    contentDescription = "View Icon",
                                    modifier = Modifier.size(15.dp).align(Alignment.CenterVertically),
                                    tint = Color.White
                                )
                            }
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(-(10).dp)
                        ) {
                            Text(
                                text = user.getTotalRated().toString(),
                                fontSize = 50.sp,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = customFontFamily,
                                ),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = "Rated!",
                                fontSize = 20.sp,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                    .padding(bottom = 20.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .background(LogoColor)
                                    .padding(horizontal = 10.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {

                                    }
                            ) {
                                Text(
                                    text = "View ",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = customFontFamily,
                                    ),
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.view_icon),
                                    contentDescription = "View Icon",
                                    modifier = Modifier.size(15.dp).align(Alignment.CenterVertically),
                                    tint = Color.White
                                )
                            }
                        }

                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(2.dp)
                                .padding(top = 10.dp, bottom = 50.dp)

                        )


                        Column(
                            verticalArrangement = Arrangement.spacedBy(-(10).dp)
                        ) {
                            Text(
                                text = user.getTotalNoted().toString(),
                                fontSize = 50.sp,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = customFontFamily,
                                ),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = "Places\nVisited",
                                fontSize = 20.sp,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                    .padding(bottom = 20.dp)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "View Archives ",
                    style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable {

                    }
                )
                Icon(
                    painter = painterResource(id = R.drawable.archive_icon),
                    contentDescription = "Archive Icon",
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.CenterVertically),
                    tint = Color.Black
                )
            }

            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Notifications",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.W500,
                    textDecoration = TextDecoration.Underline
                ),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 22.sp,
                )

            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val contentHeight = screenHeight - 575.dp

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = contentHeight)
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    val notifications = listOf(
                        "Notification 1", "Notification 2", "Notification 3",
                        "Notification 4", "Notification 5", "Notification 6",
                        "Notification 1", "Notification 2", "Notification 3", "Notification 4"
                    )

                    for (notification in notifications) {
                        Text(
                            text = notification,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                                .alpha(0.5f)
                        )

                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = {
                        authenticationController.logOut()
                        context.startActivity(Intent(context, Noted::class.java))
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowColor,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Log Out")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.log_out_icon),
                            contentDescription = "Log Out",
                            tint = Color.White
                        )
                    }
                }
            }






        }
    }

}
