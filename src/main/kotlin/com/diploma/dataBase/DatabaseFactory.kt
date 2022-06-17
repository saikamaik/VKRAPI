package com.diploma.dataBase

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.io.PrintWriter
import java.sql.Connection
import java.sql.SQLException
import java.util.*
import java.net.URI

object DatabaseFactory {

    private var ds: HikariDataSource? = null
    private val props = Properties()

    fun hikari() {
        val config = HikariConfig()
        config.maximumPoolSize = 3
        config.isAutoCommit = false

        val uri = URI(System.getenv("DATABASE_URL"))
        val username = uri.userInfo.split(":").toTypedArray()[0]
        val password = uri.userInfo.split(":").toTypedArray()[1]

        config.jdbcUrl =
            "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path + "?sslmode=require" + "&user=$username&password=$password"

        config.validate()

        Database.connect(HikariDataSource(config))
    }

}