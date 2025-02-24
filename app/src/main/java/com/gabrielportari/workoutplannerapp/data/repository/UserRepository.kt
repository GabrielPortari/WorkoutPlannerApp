package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.User

class UserRepository private constructor(context: Context){

    private val database = PlannerDatabase(context)

    companion object{
        private lateinit var repository: UserRepository
        fun getInstance(context: Context): UserRepository{
            if(!Companion::repository.isInitialized){
                repository = UserRepository(context)
            }
            return repository
        }
    }

    fun get(id: Int): User?{
        var user: User? = null
        try{
            val db = database.readableDatabase
            val projection = arrayOf(
                MyConstants.DATABASE.USER_COLUMNS.ID,
                MyConstants.DATABASE.USER_COLUMNS.NAME,
                MyConstants.DATABASE.USER_COLUMNS.ACTIVE_WEEK
            )
            val selection = MyConstants.DATABASE.USER_COLUMNS.ID + " = ?"
            val selectionArgs = arrayOf(id.toString())
            val cursor = db.query(
                MyConstants.DATABASE.USER_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            )
            if(cursor != null && cursor.count > 0){
                while(cursor.moveToNext()){
                    val userId = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.USER_COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.USER_COLUMNS.NAME))
                    val weekId = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.USER_COLUMNS.ACTIVE_WEEK))

                    user = User(userId, name, weekId)
                }
                cursor.close()
            }

        }catch (e: Exception){
            return user
        }
        return user
    }

    fun update(user: User): Boolean{
        return try{
            val db = database.writableDatabase

            val id = user.id
            val name = user.name
            val values = ContentValues()
            values.put(MyConstants.DATABASE.USER_COLUMNS.ID, user.id)
            values.put(MyConstants.DATABASE.USER_COLUMNS.NAME, user.name)

            db.update(MyConstants.DATABASE.USER_TABLE, values, null, null)
            true
        }catch (e: Exception){
            false
        }
    }

    fun selectWeek(id: Int): Boolean{
        return try {
            val db = database.readableDatabase

            val values = ContentValues()
            values.put(MyConstants.DATABASE.USER_COLUMNS.ID, MyConstants.USER_ID.ID)
            values.put(MyConstants.DATABASE.USER_COLUMNS.ACTIVE_WEEK, id)

            db.update(MyConstants.DATABASE.USER_TABLE, values, null, null)
            true
        }catch (e: Exception){
            false
        }
    }
}