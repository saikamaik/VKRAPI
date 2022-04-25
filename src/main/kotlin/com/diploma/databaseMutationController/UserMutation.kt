package com.diploma.databaseMutationController

import com.diploma.model.User
import com.diploma.model.UserData
import com.diploma.model.UserDataInput
import com.diploma.utils.JwtConfig
import com.diploma.utils.SECRET
import graphql.GraphQLException
import org.apache.commons.codec.digest.DigestUtils
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.postgresql.util.PSQLException

class UserMutation {

    fun userRegistration(data: UserData): UserData { //Регистрация юзера
        try {
            data.id = transaction {
                User.insert {
                    if (data.id != null) it[id] = data.id!!
                    it[email] = data.email!!
                    it[password] = DigestUtils.md5Hex(data.email + SECRET + data.password)
                    it[name] = data.name!!
                    it[phoneNumber] = data.phoneNumber!!
                    it[birthDate] = DateTime(data.birthDate)
                }[User.id]
            }
            data.refreshToken = JwtConfig().generateRefreshToken(data.email!!, data.id!!)
            transaction {
                User.update({ User.id eq data.id!! }) { it[refreshToken] = data.refreshToken!! }
            }
            data.accessToken = JwtConfig().generateAccessToken(data.email!!, data.id!!)
            data.password = ""
            return data
        } catch (ex: ExposedSQLException) {
            if (ex.toString().contains("User_email_key")) {
                throw GraphQLException("A User with the same email already exists")
            } else if (ex.toString().contains("User_phone_num_key")) {
                throw GraphQLException("A User with the same phone number already exists")
            }
        } catch (ex: PSQLException) {
            if (ex.toString().contains("User_email_key")) {
                throw GraphQLException("A User with the same email already exists")
            } else if (ex.toString().contains("User_phone_num_key")) {
                throw GraphQLException("A User with the same phone number already exists")
            }
        } catch (ex: java.lang.NullPointerException) {
            throw GraphQLException("All data must be defined if service provider registering now")
        }
        return data
    }

    //TODO логин и рег в графкл

    fun authUser(email: String, password: String): String? {//Авторизация юзера
        val map: List<UserData> = transaction {
            User.select {
                (User.email eq email) and (User.password eq password)
            }.map { User.toMap(it) }
        }
        var user: UserData? = null
        user = transaction {
            User.select { (User.email eq email) }.limit(1).single().let{ User.toShowMap(it)}
        }
        user.accessToken = null
        return if (map.isNotEmpty()) {
            user.refreshToken = JwtConfig().generateRefreshToken(user.email!!, user.id!!)
            user.accessToken = JwtConfig().generateAccessToken(user.email!!, user.id!!)
            user.id = user.id
            transaction {
                User.update({ User.email eq user.email!! }) {
                    it[refreshToken] = user.refreshToken!!
                    it[accessToken] = user.accessToken
                }
            }
            user.accessToken
        } else
            return null
    }

    fun deleteUser(data: Int) {
        transaction {
            User.deleteWhere { User.id eq data }
        }
    }

    fun createUser(data: UserDataInput){
        transaction {
            User.insert {
                if (data.id != null) it[id] = data.id
                it[email] = data.email!!
                it[password] = data.password!!
                it[name] = data.name!!
                it[phoneNumber] = data.phoneNumber!!
                it[birthDate] = DateTime(data.birthDate)
                it[address] = data.address!!
                it[orgId] = data.orgId!!
            }[User.id]
        }
    }

    fun updateUser(id: Int, data: UserDataInput){
        transaction {
            User.update({ User.id eq id }) {
                if(data.password != null) it[password] = data.password
                if(data.name != null) it[name] = data.name
                if(data.birthDate != null) it[birthDate] = DateTime(data.birthDate)
                if(data.phoneNumber != null) it[phoneNumber] = data.phoneNumber
                if(data.email != null) it[email] = data.email
            }
        }
    }
}


