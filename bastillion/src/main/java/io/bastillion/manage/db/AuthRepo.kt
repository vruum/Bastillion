package io.bastillion.manage.db

import io.bastillion.manage.db.entities.User
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface AuthRepo {
    @SqlQuery("SELECT * FROM users ORDER BY username")
    fun listUsers(): List<User>?

    @SqlQuery("select * from  users where lower(username) like lower(:username)")
    fun getUserByUsername(@Bind("username") userName: String): User?

    @SqlUpdate("UPDATE users set password = :password where username = :username")
    fun setUserPassword(@Bind("password") password: String, @Bind("username") username: String)

    @SqlUpdate("INSERT INTO users(username, email,password) VALUES(:username, :email, :password)")
    fun createUser(@Bind("username") username: String, @Bind("email") email: String, @Bind("password") password: String);
}