package io.bastillion.manage.controllers


import io.javalin.Javalin
import io.javalin.http.Context
import loophole.mvc.annotation.Kontrol
import loophole.mvc.annotation.MethodType


class LoginController(app: Javalin) {
    init {
        app
                .get("/login") { login(it) }
                .get("/logout") { logout() }
                .post("/loginSubmit") { loginSubmit(it) }
    }


    fun login(ctx: Context) {
        ctx.render("/login.html")
    }


    fun loginSubmit(ctx: Context) {

    }


    fun logout() {

    }
}