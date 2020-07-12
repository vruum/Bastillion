package io.bastillion.manage.db.entities

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.beans.ConstructorProperties


data class ApplicationKey(@ColumnName("id") var id: Long,
                          @ColumnName("private_key") var privateKey: String,
                          @ColumnName("public_key") var publicKey: String,
                          @ColumnName("passphrase") var passphrase: String?) {
}