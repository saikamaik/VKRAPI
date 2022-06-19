package com.diploma.databaseMutationController

import com.diploma.model.*
import com.diploma.utils.JwtConfig
import com.diploma.utils.checkEmail
import com.diploma.utils.formatDate
import graphql.GraphQLException
import org.apache.commons.codec.digest.DigestUtils
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.postgresql.util.PSQLException

class UserMutation {

    private val jwtSecret = System.getenv("JWT_SECRET")

    fun showUser(
        id: Int?,
        name: String?,
        email: String?,
        phoneNumber: String?,
        birthDate: String?,
        address: String?,
        orgId: Int?
    ): List<UserData> {
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

    fun createUser(data: UserDataInput): UserDataInput {

        validInput(data)

        data.id = transaction {
            User.insert {
                it[email] = data.email!!
                it[password] = DigestUtils.sha1Hex(data.email + jwtSecret + data.password)
                it[name] = data.name!!
                it[phoneNumber] = data.phoneNumber!!
                if (data.birthDate != null) it[birthDate] = DateTime(data.birthDate)
                else it[birthDate] = DateTime("1970-01-01")
                it[address] = data.address!!
                it[orgId] = data.orgId!!
            }[User.id]
        }

        return data
    }

    fun updateUser(
        id: Int,
        name: String?,
        birthDate: String?,
        phoneNumber: String?,
        email: String?,
        orgId: Int?,
        address: String?
    ) {
        if (email != null) {
            if (!checkEmail(email))
                throw Error("Email с неправильным форматом")
        }

        if (name != null) {
            if (name.length < 10)
                throw Error("ФИО должно быть больше 10 символов")
        }

        if (birthDate != null) {
            if (formatDate(birthDate) == null) {
                throw Error("Неправильный формат даты")
            }
        }
        transaction {
            User.update({ User.id eq id }) {
                if (name != null) it[User.name] = name
                if (birthDate != null) it[User.birthDate] = DateTime(birthDate)
                if (phoneNumber != null) it[User.phoneNumber] = phoneNumber
                if (email != null) it[User.email] = email
                if (orgId != null) it[User.orgId] = orgId
                if (address != null) it[User.address] = address
            }
        }
    }

    private fun validInput(data: UserDataInput) {
        if (!checkEmail(data.email!!))
            throw Error("Email с неправильным форматом")

        if (data.name?.length!! < 10)
            throw Error("ФИО должно быть больше 10 символов")

        if (data.password?.length!! < 8)
            throw Error("password should be minimum 8 characters")

        if (data.address.isNullOrEmpty())
            throw Error("Адрес - обязательное поле")

        if (data.orgId == null)
            throw Error("ID организации - обязательное поле")

        if (data.phoneNumber == null)
            throw Error("Номер телефона - обязательное поле")

        if (data.birthDate != null) {
            if (formatDate(data.birthDate) == null) {
                throw Error("Неправильный формат даты")
            }
        }
    }

    fun deleteUser(data: Int) {
        transaction {
            deleteWhenDeletingUser(data)
            User.deleteWhere { User.id eq data }
        }


//    fun registerUser(data: UserDataInput): UserDataInput { //Регистрация юзера
//        try {
//            val dataCreated = createUser(data)
//            dataCreated.refreshToken = JwtConfig.generateRefreshToken(dataCreated.email!!, dataCreated.id!!)
//            transaction {
//                User.update({ User.id eq dataCreated.id!! }) { it[refreshToken] = dataCreated.refreshToken!! }
//            }
//            dataCreated.accessToken = JwtConfig.generateAccessToken(dataCreated.email, dataCreated.id!!)
//            return dataCreated
//        } catch (ex: ExposedSQLException) {
//            if (ex.toString().contains("User_email_key")) {
//                throw GraphQLException("A User with the same email already exists")
//            } else if (ex.toString().contains("User_phone_num_key")) {
//                throw GraphQLException("A User with the same phone number already exists")
//            }
//        } catch (ex: PSQLException) {
//            if (ex.toString().contains("User_email_key")) {
//                throw GraphQLException("A User with the same email already exists")
//            } else if (ex.toString().contains("User_phone_num_key")) {
//                throw GraphQLException("A User with the same phone number already exists")
//            }
//        }
//        return data
//    }
//
//    fun authUser(email: String, password: String): String? {//Авторизация юзера
//        val map: List<UserData> = transaction {
//            User.select {
//                (User.email eq email) and (User.password eq DigestUtils.sha1Hex(email + jwtSecret + password))
//            }.map { User.toMap(it) }
//        }
//        return if (map.isNotEmpty()) {
//            map[0].refreshToken = JwtConfig.generateRefreshToken(map[0].email!!, map[0].id!!)
//            map[0].accessToken = JwtConfig.generateAccessToken(map[0].email!!, map[0].id!!)
//            transaction {
//                User.update({ User.id eq map[0].id!! }) {
//                    it[refreshToken] = map[0].refreshToken!!
//                }
//            }
//            map[0].accessToken
//        } else
//            return null
//    }
//
//    fun refreshUserToken(data : UserDataInput) : UserDataInput{//обновление токена пользователя
//        val values = JwtConfig.tokenDecode(data.refreshToken!!)
//        val map : List<UserData> = transaction {
//            User.select {User.refreshToken eq data.refreshToken!!}.map { User.toMap(it) }
//        }
//        if (map.isNotEmpty()){
//            transaction {
//                User.update({ User.refreshToken eq data.refreshToken!! }){
//                    data.refreshToken = JwtConfig.generateRefreshToken(values[1], values[0].toIntOrNull()!!)
//                    it[refreshToken] = data.refreshToken!!
//                }
//                data.accessToken = JwtConfig.generateAccessToken(values[1], values[0].toInt())
//            }
//        }
//        else{
//            throw GraphQLException("Invalid token was given")
//        }
//        return data
//    }

    }

    fun addApartmentToUser(data: UserApartmentDataInput) {
        transaction {
            User_Apartment.insert {
                it[userId] = data.userId!!
                it[apartmentId] = data.apartmentId!!
            }
        }
    }

    fun updateApartmentToUser(id: Int, userId: Int?, apartmentId: Int?) {
        transaction {
            User_Apartment.update({ User_Apartment.id eq id }) {
                if (userId != null) it[User_Apartment.userId] = userId
                if (apartmentId != null) it[User_Apartment.apartmentId] = apartmentId
            }
        }
    }

    fun deleteApartmentToUser(id: Int) {
        transaction {
            User_Apartment.deleteWhere { User_Apartment.id eq id }
        }
    }

    private fun deleteWhenDeletingUser(id: Int) {
        transaction {
            User_Apartment.deleteWhere { User_Apartment.userId eq id }
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


