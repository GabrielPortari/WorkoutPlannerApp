package com.gabrielportari.workoutplannerapp.data.repository.dao

import com.gabrielportari.workoutplannerapp.data.model.Exercise

interface IExerciseDAO {
    fun insert(exercise: Exercise) : Boolean

    fun update(exercise: Exercise) : Boolean

    fun delete(id: Int) : Boolean

    fun deleteWorkout(id: Int) : Boolean

    fun getExercise(id: Int) : Exercise?

    fun getAllFromWorkout(idWorkout: Int) : List<Exercise>

    fun getAllFromWorkoutExceptButton(idWorkout: Int) : List<Exercise>

}