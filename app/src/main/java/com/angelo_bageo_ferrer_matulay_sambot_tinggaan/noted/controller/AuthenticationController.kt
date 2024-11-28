package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat.startActivity
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.main.MainController
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.LogInView
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.SignUpView
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.SplashScreen
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore

class AuthenticationController() {
    private val logInView = LogInView(this)
    private val signUpView = SignUpView(this)
    private val mainController = MainController(this)
    private var auth = FirebaseAuth.getInstance()


    fun isUserAuthenticated(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    fun logOut() {
        auth.signOut()
    }




    @Composable
    fun OnLogInClick() {
        logInView.DisplayView()
    }

    @Composable
    fun OnSignUpClick() {
        signUpView.DisplayView()
    }

    fun validateLogin(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback (true)
                }
                else
                    callback(false)
            }
    }


    @Composable
    fun GoToHomePage() {
        mainController.AppNavigation()
    }


    fun validateCredentials(
        email: String,
        firstName: String,
        lastName: String,
        birthDate: String,
        password: String,
        callback: (Boolean) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        Log.v("User is not null after authentication", "hahaha")
                        val userId = user.uid
                        val userMap = hashMapOf(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "birthDate" to birthDate,
                            "email" to email
                        )

                        Log.v("Time to log the details of the user", "Time to log the details of the user")

                        val db = FirebaseFirestore.getInstance()
                        db.collection("users")
                            .document(userId)
                            .set(userMap)
                            .addOnSuccessListener {
                                Log.v("Firestoreeeeee", "User data saved successfully.")
                                callback(true)
                            }
                            .addOnFailureListener { exception ->
                                Log.v("Firestoreeeeeee", "Error saving user data: ${exception.message}")
                                callback(false)
                            }
                    } else {
                        Log.v("Auth", "User is null after authentication.")
                        callback(false)
                    }
                } else {
                    Log.v("Auth", "Authentication failed: ${task.exception?.message}")
                    callback(false)
                }
            }
    }
}