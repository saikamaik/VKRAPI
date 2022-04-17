package com.diploma.repository

import com.diploma.model.UserData

interface IUserRepository {
    fun getById(id: Int): UserData
    fun createUser(data: UserData)
    fun deleteUser(id: Int)
}