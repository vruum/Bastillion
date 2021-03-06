package io.bastillion.manage.controllers


import io.bastillion.manage.db.AuthRepo
import io.bastillion.manage.services.BCryptEncoder
import io.bastillion.manage.services.LoginService
import io.javalin.Javalin
import io.javalin.http.Context
import loophole.mvc.annotation.Kontrol
import loophole.mvc.annotation.MethodType
import kotlin.math.log


class LoginController(app: Javalin, val loginService: LoginService) {
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
        val username = ctx.formParam("auth.username")
        val password = ctx.formParam("auth.password")
        if (username != null && password != null && loginService.authenticate(username, password)) {
            ctx.sessionAttribute("user", loginService.getUser(username))
            ctx.redirect(ctx.sessionAttribute<String>("post-auth.redirect")?: "/")

        } else {
            ctx.redirect("/login?authfailed=true")
        }


    }


    fun logout() {

    }
}