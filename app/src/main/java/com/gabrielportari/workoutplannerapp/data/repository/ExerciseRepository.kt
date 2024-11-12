package com.gabrielportari.workoutplannerapp.data.repository

import android.content.Context

class ExerciseRepository private constructor(context: Context) {

    private val database = WorkoutDatabase(context)

    companion object {
        private lateinit var repository: ExerciseRepository
        fun getInstance(context: Context): ExerciseRepository {
            if (!Companion::repository.isInitialized) {
                repository = ExerciseRepository(context)
            }
            return repository
        }
    }


}