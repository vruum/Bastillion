package io.bastillion.manage.controllers

import io.javalin.Javalin


class ScriptController(app: Javalin) {
    init {
        app
                .get("/admin/viewScripts") { viewScripts() }
                .post("/admin/saveScript") { saveScript() }
                .get("/admin/deleteScript") { deleteScript() }
    }


    fun viewScripts() {

    }


    fun saveScript() {
    }


    fun deleteScript() {
    }

}