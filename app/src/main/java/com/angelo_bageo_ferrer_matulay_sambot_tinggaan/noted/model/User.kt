package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate

class User {
    private val auth = FirebaseAuth.getInstance()
    private lateinit var email: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var birthDate: String
    private lateinit var password: String
    private lateinit var notes : List<Note>
    private var totalRated : Int = 0
    private var totalNoted : Int = 0
    private var totalPlacesVisited : Int = 0

    constructor() {

    }

    constructor(email : String, firstName : String, lastName : String, birthDate : String, password : String, notes : List<Note>){
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.birthDate = birthDate
        this.password = password
        this.notes = notes
        this.totalNoted = 0
        this.totalRated = 0
    }

    constructor(email: String, firstName: String, lastName: String, birthDate: String, password: String){
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.birthDate = birthDate
        this.password = password
        this.notes = mutableListOf()
    }

    constructor(firstName: String, lastName: String) {
        this.firstName = firstName
        this.lastName = lastName
    }

    fun getEmail() : String {
        return this.email
    }

    fun getFirstName() : String {
        return this.firstName
    }

    fun getLastName() : String {
        return this.lastName
    }

    fun getBirthDate() : String {
        return this.birthDate
    }

    fun getPassword() : String {
        return this.password
    }

    fun getNotes() : List<Note> {
        return this.notes
    }

    fun getTotalRated() : Int {
        return this.totalRated
    }

    fun getTotalNoted() : Int {
        return this.totalNoted
    }

    fun setEmail(email : String) {
        this.email = email
    }

    fun setFirstName(firstName : String) {
        this.firstName = firstName
    }

    fun setLastName(lastName : String) {
        this.lastName = lastName
    }

    fun setBirthDate(birthDate : String) {
        this.birthDate = birthDate
    }

    fun setPassword(password : String) {
        this.password = password
    }

    fun incrementRated() {
        this.totalRated++
        val userRef = FirebaseFirestore.getInstance().collection("users").document(auth.currentUser?.uid ?: "")
        userRef.update("totalRated", this.totalRated)
    }

    fun incrementNoted() {
        this.totalNoted++
        val userRef = FirebaseFirestore.getInstance().collection("users").document(auth.currentUser?.uid ?: "")
        userRef.update("totalNoted", this.totalNoted)
    }

    fun getTotPlacesVisited() : Int {
        return this.totalPlacesVisited
    }


    fun incrementPlacesVisited() {
        this.totalPlacesVisited++
        val userRef = FirebaseFirestore.getInstance().collection("users").document(auth.currentUser?.uid ?: "")
        userRef.update("totalPlacesVisited", this.totalPlacesVisited)
    }

    fun setTotalRated(totalRated : Int) {
        this.totalRated = totalRated
    }

    fun setTotalNoted(totalNoted : Int) {
        this.totalNoted = totalNoted
    }

    fun setTotalPlacesVisited(totalPlacesVisited : Int) {
        this.totalPlacesVisited = totalPlacesVisited
    }


}