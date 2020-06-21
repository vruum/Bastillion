package io.bastillion.manage.controllers

import io.javalin.Javalin

class AuthKeysController(app: Javalin) {
    init {
        app
                .get("/manage/enablePublicKey") { enablePublicKey() }
                .get("/manage/disablePublicKey") { disablePublicKey() }
                .get("/manage/viewKeys") { manageViewKeys() }
                .get("/admin/viewKeys") { adminViewKeys() }
                .post("/admin/savePublicKey") { savePublicKeys() }
                .get("/admin/deletePublicKey") { deletePublicKey() }
                .get("/admin/downloadPvtKey") { downloadPvtKey() }
    }

    fun enablePublicKey() {}
    fun disablePublicKey() {}
    fun manageViewKeys() {}
    fun adminViewKeys() {}
    fun savePublicKeys() {}
    fun deletePublicKey() {}
    fun downloadPvtKey() {}

}