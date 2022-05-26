package com.diploma.databaseMutationController

import com.diploma.model.*
import com.diploma.utils.JwtConfig
import graphql.GraphQLException
import org.apache.commons.codec.digest.DigestUtils
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.postgresql.util.PSQLException

class UserMutation {

    fun userRegistration(data: UserData): UserData { //Регистрация юзера
        val jwtSecret = System.getenv("JWT_SECRET")
        data.id = null
        try {
            data.id = transaction {
                User.insert {
                    it[email] = data.email!!
                    it[password] = DigestUtils.md5Hex(data.email + jwtSecret + data.password)
                    it[name] = data.name!!
                    it[phoneNumber] = data.phoneNumber!!
                    it[birthDate] = DateTime(data.birthDate)
                }[User.id]
            }
            data.refreshToken = JwtConfig.generateRefreshToken(data.email!!, data.id!!)
            transaction {
                User.update({ User.id eq data.id!! }) { it[refreshToken] = data.refreshToken!! }
            }
            data.accessToken = JwtConfig.generateAccessToken(data.email!!, data.id!!)
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

    fun authUser(email: String, password: String): String? {//Авторизация юзера
        val map: List<UserData> = transaction {
            User.select {
                (User.email eq email) and (User.password eq password)
            }.map { User.toMap(it) }
        }
        val user: UserData?
        user = transaction {
            User.select { (User.email eq email) }.limit(1).single().let{ User.toShowMap(it)}
        }
        user.accessToken = null
        return if (map.isNotEmpty()) {
            user.refreshToken = JwtConfig.generateRefreshToken(user.email!!, user.id!!)
            user.accessToken = JwtConfig.generateAccessToken(user.email!!, user.id!!)
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

    fun refreshUserToken(data : UserData) : UserData{//обновление токена пользователя
        val values = JwtConfig.tokenDecode(data.refreshToken!!)
        val map : List<UserData> = transaction {
            User.select {User.refreshToken eq data.refreshToken!!}.map { User.toMap(it) }
        }
        if (map.isNotEmpty()){
            transaction {
                User.update({ User.refreshToken eq data.refreshToken!! }){
                    data.refreshToken = JwtConfig.generateRefreshToken(values[1], values[0].toIntOrNull()!!)
                    it[refreshToken] = data.refreshToken!!
                }
                data.accessToken = JwtConfig.generateAccessToken(values[1], values[0].toInt())
            }
        }
        else{
            throw GraphQLException("Invalid token was given")
        }
        return data
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

    fun addApartmentToUser(data: UserApartmentData) {
        transaction {
            User_Apartment.insert {
                it[userId] = data.userId
                it[apartmentId] = data.apartmentId
            }
        }
    }

    fun deleteApartmentToUser(id: Int) {
        transaction {
            User_Apartment.deleteWhere { User_Apartment.id eq id }
        }
    }
}


