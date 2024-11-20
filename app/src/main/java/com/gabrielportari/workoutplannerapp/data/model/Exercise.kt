package com.gabrielportari.workoutplannerapp.data.model

data class Exercise(
    val exerciseId: Int,
    val workoutId: Int,
    var name: String,
    val description: String,
    val repCount: String,
) {
}