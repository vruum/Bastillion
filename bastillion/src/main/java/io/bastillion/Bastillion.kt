package io.bastillion

import io.bastillion.manage.control.ProfileKtrl
import io.bastillion.manage.controllers.AuthKeysController
import io.bastillion.manage.controllers.LoginController
import io.bastillion.manage.controllers.OTPController
import io.bastillion.manage.controllers.ProfileController
import io.javalin.Javalin
import io.javalin.http.Context


fun main(args: Array<String>) {
    val app = Javalin.create()
            .start(7000)
    AuthKeysController(app)
    LoginController(app)
    OTPController(app)
    ProfileController(app)


}