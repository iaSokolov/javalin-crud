package ru.sokolov.connection

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

class DataSourceContext(private val connection: Connection) {
    fun getContext(): DSLContext {
        return DSL.using(connection, SQLDialect.POSTGRES)
    }
}