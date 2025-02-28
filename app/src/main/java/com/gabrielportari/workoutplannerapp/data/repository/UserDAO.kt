package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.User
import com.gabrielportari.workoutplannerapp.data.repository.dao.IUserDAO

class UserDAO private constructor(context: Context) : IUserDAO{

    private val database = PlannerDatabase(context)

    companion object{
        private lateinit var repository: UserDAO
        fun getInstance(context: Context): UserDAO{
            if(!Companion::repository.isInitialized){
                repository = UserDAO(context)
            }
            return repository
        }
    }

    override fun get(id: Int): User?{
        var user: User? = null
        val db = database.readableDatabase

        try{
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
        }finally {
            db.close()
        }

        return user
    }

    override fun update(user: User): Boolean{

        val db = database.writableDatabase

        return try{
            val id = user.id
            val name = user.name
            val values = ContentValues()
            values.put(MyConstants.DATABASE.USER_COLUMNS.ID, user.id)
            values.put(MyConstants.DATABASE.USER_COLUMNS.NAME, user.name)

            db.update(MyConstants.DATABASE.USER_TABLE, values, null, null)
            true
        }catch (e: Exception){
            false
        }finally {
            db.close()
        }
    }

    override fun selectWeek(id: Int): Boolean{
        val db = database.readableDatabase

        return try {

            val values = ContentValues()
            values.put(MyConstants.DATABASE.USER_COLUMNS.ID, MyConstants.USER_ID.ID)
            values.put(MyConstants.DATABASE.USER_COLUMNS.ACTIVE_WEEK, id)

            db.update(MyConstants.DATABASE.USER_TABLE, values, null, null)
            true
        }catch (e: Exception){
            false
        }finally {
            db.close()
        }
    }
}