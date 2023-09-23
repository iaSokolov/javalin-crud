package ru.sokolov.connection

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

class DataSource {
    companion object {
        private var dataSource: HikariDataSource = HikariDataSource(
            HikariConfig("./datasource.properties")
        )
    }

    fun getConnection(): Connection = dataSource.getConnection()
}