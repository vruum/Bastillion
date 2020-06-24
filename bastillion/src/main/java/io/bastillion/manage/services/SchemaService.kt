package io.bastillion.manage.services

import io.bastillion.db.SchemaDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.SqlObjectPlugin


class SchemaService(val schema: SchemaDao) {

    fun initialiseSchema(overrideExisting: Boolean) {

        if (overrideExisting) {
            schema.deleteApplicationKey()
            schema.deleteLogins()
            schema.deleteProfiles()
            schema.deletePublicKeys()
            schema.deleteScripts()
            schema.deleteStatus()
            schema.deleteSystems()
            schema.deleteSystemsLogs()
            schema.deleteSystemsMap()
            schema.deleteTerminalLogs()
            schema.deleteUserThemes()
            schema.deleteUsers()
            schema.deleteUsersMap()
        }
        schema.createUsers()
        schema.createApplicationKey()
        schema.createLogins()
        schema.createProfiles()
        schema.createPublicKeys()
        schema.createScripts()
        schema.createSystems()
        schema.createSystemsLogs()
        schema.createSystemsMap()
        schema.createStatus()
        schema.createTerminalLogs()
        schema.createUserThemes()
        schema.createUsers()
        schema.createUsersMap()
    }
}