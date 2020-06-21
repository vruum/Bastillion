package io.bastillion.manage.controllers


import io.javalin.Javalin
import loophole.mvc.annotation.Kontrol
import loophole.mvc.annotation.MethodType


class LoginController(app: Javalin) {
    init {
        app
                .get("/login") { login() }
                .get("/logout") { logout() }
                .post("/loginSubmit") { loginSubmit() }
    }


    fun login() {

    }


    fun loginSubmit() {

    }


    fun logout() {

    }
}