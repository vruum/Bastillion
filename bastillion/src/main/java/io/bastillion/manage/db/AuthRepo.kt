package io.bastillion.manage.db

import io.bastillion.manage.db.entities.User
import org.jdbi.v3.sqlobject.statement.SqlQuery

interface AuthRepo {
    @SqlQuery("SELECT * FROM user ORDER BY name")
    fun listUsers(): List<User>?
}