package com.gabrielportari.workoutplannerapp.data.model

class Exercise(
    val exerciseId: Int,
    val workoutId: Int,
    var name: String,
    var description: String,
    var repCount: String,
) {
}