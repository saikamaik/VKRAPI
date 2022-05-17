package com.diploma.model

import com.diploma.utils.JwtConfig
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

data class LoginBody(
    val email: String,
    val password: String
)

data class UserData(
    var id: Int? = null,
    var name: String? = "",
    var email: String? = "",
    var password: String? = "",
    var phoneNumber: String? = "",
    var birthDate: String? = "",
    var address: String? = "",
    var orgId: Int? = null,
    var refreshToken: String? = "",
    var accessToken: String? = ""
)

data class UserDataInput(
    val id: Int? = null,
    val name: String? = "",
    val email: String? = "",
    val password: String? = "",
    val phoneNumber: String? = "",
    val birthDate: String? = "",
    val address: String? = "",
    var orgId: Int? = 1
)

object User: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val phoneNumber = varchar("phone_number", 30)
    val birthDate = datetime("birth_date").nullable()
    val address = varchar("address", 255)
    val accessToken = varchar("access_token", 500).nullable()
    val refreshToken = varchar("refresh_token", 500).nullable()
    val orgId = (integer("org_id") references Organization.id)

    override val primaryKey = PrimaryKey(id, name = "user_pk")

    fun toMap(row: ResultRow): UserData =
        UserData(
            id = row[id],
            name = row[name],
            email = row[email],
            password = row[password],
            phoneNumber = row[phoneNumber],
            birthDate = row[birthDate]!!.toString("dd-MM-yyyy"),
            address = row[address],
            orgId = row[orgId],
            accessToken = JwtConfig().generateAccessToken(row[email], row[id]),
            refreshToken = JwtConfig().generateRefreshToken(row[email], row[id])
        )

    fun toShowMap(row: ResultRow): UserData =
        UserData(
            id = row[id],
            name = row[name],
            email = row[email],
            password = row[password],
            phoneNumber = row[phoneNumber],
            birthDate = row[birthDate]!!.toString("dd-MM-yyyy"),
            address = row[address],
        )
}

data class UserApartmentData(
    val id: Int,
    val userId: Int,
    val apartmentId: Int
)

object User_Apartment: Table() {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id") references User.id
    val apartmentId = integer("apartment_id") references Apartment.id

    override val primaryKey = PrimaryKey(id, name = "user_apartment_pk")
}
