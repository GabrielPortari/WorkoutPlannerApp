package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.User

class UserRepository private constructor(context: Context){

    private val database = PlannerDatabase(context)
    private val userRepository = UserRepository.getInstance(context)

    companion object{
        private lateinit var repository: UserRepository
        fun getInstance(context: Context): UserRepository{
            if(!Companion::repository.isInitialized){
                repository = UserRepository(context)
            }
            return repository
        }
    }

    fun update(user: User): Boolean{
        return try{
            val db = database.writableDatabase
            val name = user.name

            val values = ContentValues()
            values.put(MyConstants.DATABASE.USER_COLUMNS.NAME, user.name)
            db.update(MyConstants.DATABASE.USER_TABLE, values, null, null)
            true
        }catch (e: Exception){
            false
        }
    }

}