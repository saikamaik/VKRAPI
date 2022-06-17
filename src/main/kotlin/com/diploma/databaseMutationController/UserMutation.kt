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

    private val jwtSecret = System.getenv("JWT_SECRET")

    fun userRegistration(data: UserDataInput): UserDataInput { //Регистрация юзера
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
                (User.email eq email) and (User.password eq DigestUtils.md5Hex(email + jwtSecret + password))
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

    fun refreshUserToken(data : UserDataInput) : UserDataInput{//обновление токена пользователя
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

    fun showUser(id: Int?, name: String?, email: String?, phoneNumber: String?, birthDate: String?, address: String?, orgId: Int?): List<UserData>{
        val date = DateTime(birthDate)
        return when {
            id != null -> {
                User.select { User.id eq id }.map { User.toMap(it) }
            }
            name != null -> {
                User.select { User.name eq name }.map { User.toMap(it) }
            }
            email != null -> {
                User.select { User.email eq email }.map { User.toMap(it) }
            }
            phoneNumber != null -> {
                User.select { User.phoneNumber eq phoneNumber }.map { User.toMap(it) }
            }
            birthDate != null -> {
                User.select { User.birthDate eq date }.map { User.toMap(it) }
            }
            address != null -> {
                User.select { User.address eq address }.map { User.toMap(it) }
            }
            orgId != null -> {
                User.select { User.orgId eq orgId }.map { User.toMap(it) }
            }
            else -> User.selectAll().map { User.toMap(it) }
        }
    }

    fun createUser(data: UserDataInput): UserDataInput{
        data.id = transaction {
            User.insert {
                it[email] = data.email!!
                it[password] = DigestUtils.md5Hex(data.email + jwtSecret + data.password)
                it[name] = data.name!!
                it[phoneNumber] = data.phoneNumber!!
                it[birthDate] = DateTime(data.birthDate)
                it[address] = data.address!!
                it[orgId] = data.orgId!!
            }[User.id]
        }
        return data
    }

    fun updateUser(id: Int, name: String?, birthDate: String?, phoneNumber: String?, email: String?){
        transaction {
            User.update({ User.id eq id }) {
                if(name != null) it[User.name] = name
                if(birthDate != null) it[User.birthDate] = DateTime(birthDate)
                if(phoneNumber != null) it[User.phoneNumber] = phoneNumber
                if(email != null) it[User.email] = email
            }
        }
    }

    fun addApartmentToUser(data: UserApartmentDataInput) {
        transaction {
            User_Apartment.insert {
                it[userId] = data.userId!!
                it[apartmentId] = data.apartmentId!!
            }
        }
    }

    fun updateApartmentToUser(id:Int, userId: Int?, apartmentId: Int?) {
        transaction {
            User_Apartment.update ({ User_Apartment.id eq id }) {
                if (userId!=null) it[User_Apartment.userId] = userId
                if (apartmentId!=null) it[User_Apartment.apartmentId] = apartmentId
                }
            }
    }

    fun deleteApartmentToUser(id: Int) {
        transaction {
            User_Apartment.deleteWhere { User_Apartment.id eq id }
        }
    }

    fun showUserApartment(id: Int?, userId: Int?, apartmentId: Int?): List<UserApartmentData> {
        return when {
            id != null -> {
                User_Apartment.select { User_Apartment.id eq id }.map { User_Apartment.toMap(it) }
            }
            userId != null -> {
                User_Apartment.select { User_Apartment.userId eq userId }.map { User_Apartment.toMap(it) }
            }
            apartmentId != null -> {
                User_Apartment.select { User_Apartment.apartmentId eq apartmentId }.map { User_Apartment.toMap(it) }
            }
            else -> User_Apartment.selectAll().map { User_Apartment.toMap(it) }
        }
    }
}


