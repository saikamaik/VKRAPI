package com.diploma

import com.diploma.dataBase.DatabaseFactory
import com.diploma.model.Organization
import com.diploma.model.Position
import com.diploma.model.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import org.joda.time.DateTime

fun init() {

    transaction {

//        SchemaUtils.create(Organization)
//        Organization.insert {
//            it[name] = "Test1"
//        }
//        Organization.insert {
//            it[name] = "Test2"
//        }

//        SchemaUtils.create(User)
//        User.insert {
//            it[name] = "Robert Ignatiev"
//            it[email] = "mrobshaw@att.net"
//            it[password] = "rbQV3caC"
//            it[phoneNumber] = "(327) 221-6836"
//            it[birthDate] = DateTime("1998-02-12")
//            it[refreshToken] = "kjfdj545"
//            it[address] = ""
//            it[orgId] = 1
//        }
//        User.insert {
//            it[name] = "Dina Chaykovsky"
//            it[email] = "scottzed@optonline.net"
//            it[password] = "rmtfLWFH"
//            it[phoneNumber] = "(294) 531-9721"
//            it[birthDate] = DateTime("1998-02-12")
//            it[refreshToken] = "kjfdj5"
//            it[address] = ""
//            it[orgId] = 2
//        }
//        User.insert {
//            it[name] = "Dmitriy Alekseev"
//            it[email] = "thaljef@optonline.net"
//            it[password] = "MW8xG9hd"
//            it[phoneNumber] = "(624) 863-1204"
//            it[birthDate] = DateTime("1998-02-12")
//            it[refreshToken] = "kjfdj545"
//            it[address] = ""
//            it[orgId] = 1
//        }
    }
}

