package io.bastillion.manage.controllers

import io.bastillion.manage.db.ProfileDB
import io.bastillion.manage.db.UserDB
import io.bastillion.manage.db.UserProfileDB
import io.bastillion.manage.util.RefreshAuthKeyUtil
import io.javalin.Javalin
import loophole.mvc.annotation.Kontrol
import loophole.mvc.annotation.MethodType

class ProfileUsersController(app: Javalin) {
    init {
        app.get("/manage/viewProfileUsers") { viewProfileUsers() }
                .post("/manage/assignUsersToProfile") { assignSystemsToProfile() }
    }

    fun viewProfileUsers() {
    }


    fun assignSystemsToProfile(){
    }

}