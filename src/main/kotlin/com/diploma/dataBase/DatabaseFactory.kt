package com.diploma.dataBase

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.io.PrintWriter
import java.sql.Connection
import java.sql.SQLException
import java.util.*

object DatabaseFactory {

    private var ds: HikariDataSource? = null
    private val props = Properties()

    fun dbInnit(){
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
        props.setProperty("dataSource.user", "postgres")
        props.setProperty("dataSource.password", "847261935")
        props.setProperty("dataSource.databaseName", "postgres")
        props.setProperty("maximumPoolSize", "10")
        props["dataSource.logWriter"] = PrintWriter(System.out)
        val config = HikariConfig(props)
        config.schema = "vkr"
        Database.connect(HikariDataSource(config))
    }

}