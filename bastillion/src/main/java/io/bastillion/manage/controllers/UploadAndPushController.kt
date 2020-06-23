package io.bastillion.manage.controllers

import io.javalin.Javalin


class UploadAndPushController(app: Javalin) {
    init {
        app
                .get("/admin/setUpload") { setUpload() }
                .post("/admin/uploadSubmit") { uploadSubmit() }
                .post("/admin/push") { push() }
    }

    fun setUpload() {
    }


    fun uploadSubmit() {

    }

    fun push() {

    }
}