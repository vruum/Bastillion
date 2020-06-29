package io.bastillion.manage.services

import io.bastillion.manage.db.AuthRepo
import io.bastillion.manage.db.entities.User

class LoginService(val bcrypt: BCryptEncoder, val repo: AuthRepo) {

    fun authenticate(username: String, password: String): Boolean {
        return repo.getUserByUsername(username)
                ?.let {
                    bcrypt.matches(password, it.password)
                } ?: false
    }

    fun resetPassword(username: String, newPassword: String): User? {
        return repo.getUserByUsername(username)?.let {
            repo.setUserPassword(username, bcrypt.hashAndSalt(newPassword))
            it
        }
    }

    fun createUser(username: String, password: String, email: String) {
        repo.createUser(username, email, bcrypt.hashAndSalt(password))
    }


}