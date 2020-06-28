package io.bastillion.manage.db

import io.bastillion.manage.db.entities.User
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery

interface AuthRepo {
    @SqlQuery("SELECT * FROM user ORDER BY name")
    fun listUsers(): List<User>?

    @SqlQuery("select * from  users where lower(username) like lower(:username)")
    fun getUserByUsername(@Bind("username") userName: String): User?
}