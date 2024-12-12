package com.gabrielportari.workoutplannerapp.data.model

data class WorkoutDay(
    val key: String,
    val day: String,
    val workout: Workout?
) {
}