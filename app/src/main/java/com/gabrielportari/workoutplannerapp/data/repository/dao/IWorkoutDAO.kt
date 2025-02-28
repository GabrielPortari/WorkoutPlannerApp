package com.gabrielportari.workoutplannerapp.data.repository.dao

import com.gabrielportari.workoutplannerapp.data.model.Workout

interface IWorkoutDAO {
    fun insert(workout: Workout): Boolean

    fun update(workout: Workout): Boolean

    fun delete(id: Int): Boolean

    fun get(id: Int): Workout?

    fun getAll(): List<Workout>

    fun getAllExceptController(): List<Workout>

}