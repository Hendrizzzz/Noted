package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.model.service

class UserService {

    fun validateCredentials(email: String, firstName: String, lastName: String, birthDate: String, password: String): Boolean {
        return true
    }

    fun validateLogin(email: String, password: String): Boolean {
        return !(email.isEmpty() || password.isEmpty())
    }

}