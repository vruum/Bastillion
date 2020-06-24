package io.bastillion.manage.services

import io.bastillion.manage.model.User
import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import org.slf4j.LoggerFactory

class AuthenticationService : AccessManager {
    override fun manage(handler: Handler, ctx: Context, permittedRoles: MutableSet<Role>) {
        if (permittedRoles.isEmpty() || permittedRoles.contains(Roles.ANONYMOUS)) {
            handler.handle(ctx)
        } else {
            val user = ctx.sessionAttribute<User>("user")
            if (user != null) {
                handler.handle(ctx)
            } else {
                log.info("User not authenticated. redirecting to /login")
                ctx.redirect("/login")
            }
        }

    }

    companion object logger {
        val log = LoggerFactory.getLogger(AuthenticationService::class.java)
    }
}

enum class Roles : Role {
    MANAGER, ADMIN, ANONYMOUS
}
