package io.bastillion.manage.controllers

import io.javalin.Javalin
import io.javalin.http.Context

class IndexController(app: Javalin) {
    init {
        app.get("/") { ctx -> index(ctx) }
    }

    fun index(ctx: Context) {
        ctx.render("/index.html")
    }
}