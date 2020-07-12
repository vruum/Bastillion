package io.bastillion.manage.db.entities

import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.statement.StatementContext
import java.sql.PreparedStatement

open abstract class WildCardArgument(val s: String) : Argument {
    override fun apply(position: Int, statement: PreparedStatement?, ctx: StatementContext?) {
        statement?.setString(position, s);
    }
}

data class prefixedWildCard(private val raw: String) : WildCardArgument("%$raw")
data class postfixedWildCard(private val raw: String) : WildCardArgument("$raw%")
data class bothWildCard(private val raw: String) : WildCardArgument("%$raw%")