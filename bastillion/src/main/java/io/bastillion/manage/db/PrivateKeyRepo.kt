package io.bastillion.manage.db

import io.bastillion.manage.db.entities.ApplicationKey
import org.jdbi.v3.sqlobject.statement.SqlQuery

interface PrivateKeyRepo {

    @SqlQuery("select * from  application_key")
    fun getApplicationKey(): ApplicationKey?
}