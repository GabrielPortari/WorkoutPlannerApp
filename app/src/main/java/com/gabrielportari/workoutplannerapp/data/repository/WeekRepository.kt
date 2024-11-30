package com.gabrielportari.workoutplannerapp.data.repository

import android.content.Context
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
        return true
    }

    fun update(week: Week) : Boolean{
        return true
    }

    fun delete(id: Int) : Boolean{
        return true
    }

    fun get(id: Int) : Week{
        return Week(0, "", "", 0, 0, 0, 0, 0, 0, 0, 0)
    }

    fun getAll() : List<Week>{
        return emptyList()
    }
}