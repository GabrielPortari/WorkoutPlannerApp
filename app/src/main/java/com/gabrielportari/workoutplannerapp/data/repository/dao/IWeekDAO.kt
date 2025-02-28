package com.gabrielportari.workoutplannerapp.data.repository.dao

import com.gabrielportari.workoutplannerapp.data.model.Week

interface IWeekDAO {

    fun insert(week: Week) : Boolean

    fun update(week: Week) : Boolean

    fun workoutDeleted(id: Int) : Boolean

    fun insertWorkoutDay(day: String, weekId: Int, id: Int): Boolean

    fun deleteWorkoutDay(week: Week, day: String) : Boolean

    fun delete(id: Int) : Boolean

    fun get(id: Int) : Week?

    fun getAll() : List<Week>

    fun getAllExceptController() : List<Week>

}