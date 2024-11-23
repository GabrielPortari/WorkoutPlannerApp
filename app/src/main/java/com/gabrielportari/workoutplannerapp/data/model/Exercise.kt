package com.gabrielportari.workoutplannerapp.data.model

data class Exercise(
    val id: Int,
    val workoutId: Int,
    var name: String,
    val description: String,
    val repCount: String,
    val controller: Int
) {
}