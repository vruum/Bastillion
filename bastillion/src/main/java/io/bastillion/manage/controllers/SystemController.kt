package io.bastillion.manage.controllers


import io.javalin.Javalin

class SystemController(app: Javalin) {
    init {
        app
                .get("/admin/viewSystems") { viewAdminSystems() }
                .get("/manage/viewSystems") { viewManageSystems() }
                .post("/manage/saveSystem") { saveSystem() }
                .get("/manage/deleteSystem") { deleteSystem() }
    }

    fun viewAdminSystems() {
    }

    fun viewManageSystems() {
    }

    fun saveSystem() {
    }

    fun deleteSystem() {
    }


}