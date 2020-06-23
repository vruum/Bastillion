package io.bastillion.manage.controllers

import io.javalin.Javalin
import io.javalin.http.Context

class AuthKeysController(app: Javalin) {
    init {
        app
                .get("/manage/enablePublicKey") { ctx -> enablePublicKey(ctx) }
                .get("/manage/disablePublicKey") { disablePublicKey() }
                .get("/manage/viewKeys") { manageViewKeys() }
                .get("/admin/viewKeys") { adminViewKeys() }
                .post("/admin/savePublicKey") { savePublicKeys() }
                .get("/admin/deletePublicKey") { deletePublicKey() }
                .get("/admin/downloadPvtKey") { downloadPvtKey() }
    }

    fun enablePublicKey(ctx: Context) {
        ctx.render("error.html")
    }

    fun disablePublicKey() {}
    fun manageViewKeys() {}
    fun adminViewKeys() {}
    fun savePublicKeys() {}
    fun deletePublicKey() {}
    fun downloadPvtKey() {}

}