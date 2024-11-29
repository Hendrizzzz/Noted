package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.controller

import android.util.Log
import androidx.compose.runtime.Composable
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.main.MainController
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.LogInView
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view.SignUpView
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
        if (email.isEmpty() || password.isEmpty())
            throw IllegalArgumentException("Email and password cannot be empty.")

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback (true)
                }
                else
                    callback(false)
            }
    }


    /**
     *
     */
    @Composable
    fun GoToHomePage() {
        mainController.AppNavigation()
    }


    /**
     * @throws IllegalArgumentException if any of the input fields are empty or invalid.
     */
    fun validateCredentials(
        email: String,
        firstName: String,
        lastName: String,
        birthDate: String,
        password: String,
        confirmPassword: String,
        isTermsAndPoliciesChecked: Boolean,
        callback: (Boolean) -> Unit
    ) {
        validateInputs(email, firstName, lastName, birthDate, password, confirmPassword, isTermsAndPoliciesChecked)

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

    private fun validateInputs(
        email: String,
        firstName: String,
        lastName: String,
        birthDate: String,
        password: String,
        confirmPassword: String,
        isTermsAndPoliciesChecked: Boolean
    ) {
        if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || birthDate.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
            throw IllegalArgumentException("All fields must be filled.")
        else if (!email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")))
            throw IllegalArgumentException("Invalid email format.")
        else if (!firstName.matches(Regex("[a-zA-Z]+")))
            throw IllegalArgumentException("First name must contain only letters.")
        else if (!lastName.matches(Regex("[a-zA-Z]+")))
            throw IllegalArgumentException("Last name must contain only letters.")
        else if (password.length < 8)
            throw IllegalArgumentException("Password must be at least 8 characters long.")
        else if (password != confirmPassword)
            throw IllegalArgumentException("Passwords do not match.")
        else if (!isTermsAndPoliciesChecked)
            throw IllegalArgumentException("You must accept the terms and policies to continue.")
    }
}