package io.bastillion.manage.controllers

import io.javalin.Javalin
import loophole.mvc.annotation.Kontrol
import loophole.mvc.annotation.MethodType

class ProfileSystemsController(app: Javalin) {
    init {
        app
                .get("/manage/viewProfileSystems") { viewProfileSystems() }
                .post("/manage/assignSystemsToProfile") { assignSystemsToProfile() }
    }


    fun viewProfileSystems() {

    }

    fun assignSystemsToProfile() {

    }
}