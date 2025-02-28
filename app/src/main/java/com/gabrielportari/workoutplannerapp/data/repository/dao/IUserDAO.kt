package com.gabrielportari.workoutplannerapp.data.repository.dao

import com.gabrielportari.workoutplannerapp.data.model.User

interface IUserDAO {
    fun get(id: Int): User?

    fun update(user: User): Boolean

    fun selectWeek(id: Int): Boolean
}