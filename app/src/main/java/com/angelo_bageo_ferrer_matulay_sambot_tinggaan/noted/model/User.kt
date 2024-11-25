package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.model

import java.time.LocalDate

class User {
    private var email: String
    private var firstName: String
    private var lastName: String
    private var birthDate: LocalDate
    private var password: String

    constructor(email : String, firstName : String, lastName : String, birthDate : LocalDate, password : String){
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.birthDate = birthDate
        this.password = password
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

    fun getBirthDate() : LocalDate {
        return this.birthDate
    }

    fun getPassword() : String {
        return this.password
    }

    fun setFirstName(firstName : String) {
        this.firstName = firstName
    }

    fun setLastName(lastName : String) {
        this.lastName = lastName
    }

    fun setBirthDate(birthDate : LocalDate) {
        this.birthDate = birthDate
    }

    fun setPassword(password : String) {
        this.password = password
    }


}