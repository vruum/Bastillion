package io.bastillion.manage.controllers

import io.bastillion.common.util.AuthUtil
import io.bastillion.manage.control.UsersKtrl
import io.bastillion.manage.db.PublicKeyDB
import io.bastillion.manage.db.UserDB
import io.bastillion.manage.model.Auth
import io.bastillion.manage.util.PasswordUtil
import io.bastillion.manage.util.RefreshAuthKeyUtil
import io.javalin.Javalin
import loophole.mvc.annotation.Kontrol
import loophole.mvc.annotation.MethodType
import loophole.mvc.annotation.Validate

class UsersController(app: Javalin) {
    init {
        app
                .get("/manage/viewUsers") { viewUsers() }
                .post("/manage/saveUser") { saveUser() }
                .get("/manage/deleteUser") { deleteUser() }
                .get("/manage/unlockUser") { unlockUser() }
    }

    fun viewUsers() {
    }

    fun saveUser() {
    }

    fun deleteUser() {
    }

    fun unlockUser() {
    }


}
