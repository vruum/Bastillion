package io.bastillion.manage.controllers

import io.javalin.Javalin


class UserSettingsController(app: Javalin) {
    init {
        app
                .get("/admin/userSettings") { userSettings() }
                .post("/admin/passwordSubmit") { passwordSubmit() }
                .post("/admin/themeSubmit") { themeSubmit() }
    }

    fun userSettings() {
    }

    fun passwordSubmit() {
    }

    fun themeSubmit() {
    }


}