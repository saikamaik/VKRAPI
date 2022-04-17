package com.diploma.repository

import com.diploma.model.UserData
import com.diploma.utils.convertToSqlDate

class UserRepository: IUserRepository {

    override fun getById(id: Int): UserData {
        return try {
            users.find { it.id == id } ?: throw Exception("No users with that ID exists")
        } catch(e: Throwable) {
            throw Exception("Cannot find user")
        }
    }

    override fun createUser(data: UserData) {
        users.add(data)
    }

    override fun deleteUser(id: Int) {
        users.removeIf { it.id == id }
    }





    val users = mutableListOf(
        UserData(1, "Robert Ignatiev", "mrobshaw@att.net", "rbQV3caC",
            "(327) 221-6836", "1998-02-12", "---", 1),
        UserData(2, "Dina Chaykovsky", "scottzed@optonline.net", "PmtfLWFH",
            "(294) 531-9721", "1998-02-12", "---", 1),
        UserData(3, "Dmitriy Alekseev", "thaljef@optonline.net", "MW8xG9hd",
            "(624) 863-1204", "1998-02-12", "---", 1)
    )
}