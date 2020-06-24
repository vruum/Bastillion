package io.bastillion.db

import io.bastillion.db.entities.User
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper

import org.jdbi.v3.sqlobject.statement.SqlQuery




interface AuthDao {
    @SqlQuery("SELECT * FROM user ORDER BY name")
    fun listUsers(): List<User>?
}