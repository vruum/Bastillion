package io.bastillion.manage.controllers


import io.bastillion.manage.db.AuthRepo
import io.bastillion.manage.services.BCryptEncoder
import io.javalin.Javalin
import io.javalin.http.Context
import loophole.mvc.annotation.Kontrol
import loophole.mvc.annotation.MethodType


class LoginController(app: Javalin, val repository: AuthRepo, val bcrypt: BCryptEncoder) {
    init {
        app
                .get("/login") { login(it) }
                .get("/logout") { logout() }
                .post("/loginSubmit") { loginSubmit(it) }
    }


    fun login(ctx: Context) {
        if (ctx.queryParam("authfailed") != null) {
            val hashMap = HashMap<String, Any>()
            hashMap.put("authfailed", true)
            ctx.render("/login.html", hashMap)

        } else {
            ctx.render("/login.html")
        }
    }


    fun loginSubmit(ctx: Context) {
        val formParamMap = ctx.formParamMap()
        val username = ctx.formParam("auth.username")
        val password = ctx.formParam("auth.password")
        if (username != null && password != null) {
            val userByUsername = repository.getUserByUsername(username)
            if (userByUsername != null) {
                ctx.redirect("/happyGoLucky")
            } else {
                ctx.redirect("/login?authfailed")
            }
        } else {
            ctx.redirect("/login?authfailed")
        }


    }


    fun logout() {

    }
}