package io.bastillion.manage.controllers

import io.bastillion.manage.db.ProfileDB
import io.javalin.Javalin
import loophole.mvc.annotation.Kontrol
import loophole.mvc.annotation.MethodType

class ProfileController(app: Javalin) {
    init {
        app
                .get("/manage/viewProfiles") { viewProfiles() }
                .post("/manage/saveProfile") { saveProfile() }
                .get("/manage/deleteProfile") { deleteProfile() }
    }

    fun viewProfiles() {

    }

    fun saveProfile() {

    }


    fun deleteProfile() {

    }
}