package io.bastillion.manage.controllers

import io.bastillion.manage.services.Roles
import io.javalin.Javalin
import io.javalin.core.security.SecurityUtil.roles
import io.javalin.http.Context

class AuthKeysController(app: Javalin) {
    init {
        app
                .get("/manage/enablePublicKey", { ctx -> enablePublicKey(ctx) }, roles(Roles.MANAGER))
                .get("/manage/disablePublicKey", { disablePublicKey(it) }, roles(Roles.MANAGER))
                .get("/manage/viewKeys", { manageViewKeys(it) }, roles(Roles.MANAGER))
                .get("/admin/viewKeys", { adminViewKeys(it) }, roles(Roles.MANAGER))
                .post("/admin/savePublicKey", { savePublicKeys(it) }, roles(Roles.ADMIN))
                .get("/admin/deletePublicKey", { deletePublicKey(it) }, roles(Roles.ADMIN))
                .get("/admin/downloadPvtKey", { downloadPvtKey(it) }, roles(Roles.ADMIN))
    }

    fun enablePublicKey(ctx: Context) {
        ctx.render("/manage/view_keys.html")
    }

    fun disablePublicKey(ctx: Context) {
        ctx.render("/manage/view_keys.html")
    }

    fun manageViewKeys(ctx: Context) {
        ctx.render("/manage/view_keys.html")
    }

    fun adminViewKeys(ctx: Context) {
        ctx.render("/admin/view_keys.html")
    }

    fun savePublicKeys(ctx: Context) {
        ctx.render("/manage/view_keys.html")
    }

    fun deletePublicKey(ctx: Context) {
        ctx.render("/manage/view_keys.html")
    }

    fun downloadPvtKey(ctx: Context) {
        ctx.render("/manage/view_keys.html")
    }

}