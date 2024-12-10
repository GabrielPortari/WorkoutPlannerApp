package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Week

class WeekRepository private constructor(context: Context){

    private val database = PlannerDatabase(context)

    companion object{
        private lateinit var repository: WeekRepository
        fun getInstance(context: Context): WeekRepository{
            if(!Companion::repository.isInitialized){
                repository = WeekRepository(context)
            }
            return repository
        }
    }

    //todo TERMINAR A IMPLEMENTAÇÃO DO REPOSITORY
    fun insert(week: Week) : Boolean{
        return try{
            val db = database.writableDatabase

            val values = ContentValues()
            val name = week.name
            val description = week.description
            val controller = week.controller

            values.put(MyConstants.DATABASE.WEEK_COLUMNS.NAME, name)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION, description)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER, controller)

            db.insert(MyConstants.DATABASE.WEEK_TABLE_NAME, null, values)

            true
        }catch(e : Exception){
            false
        }
    }

    fun update(week: Week) : Boolean{
        return try {
            val db = database.writableDatabase

            val id = week.id
            val name = week.name
            val description = week.description

            val values = ContentValues()

            true
        }catch (e: Exception){
            false
        }
    }

    fun delete(id: Int) : Boolean{
        return try{
            val db = database.writableDatabase

            val selection = MyConstants.DATABASE.WEEK_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(MyConstants.DATABASE.WEEK_TABLE_NAME, selection, args)

            true
        }catch (e: Exception){
            false
        }
    }

    fun get(id: Int) : Week{
        return Week(0, "", "", 0, 0, 0, 0, 0, 0, 0, 0)
    }

    fun getAll() : List<Week>{
        return emptyList()
    }
}