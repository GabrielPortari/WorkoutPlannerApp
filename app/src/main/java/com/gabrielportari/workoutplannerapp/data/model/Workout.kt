package com.gabrielportari.workoutplannerapp.data.model

class Workout(
    var idWorkout: Int = 0,
    var name: String,
    var description: String,
    var exercises: List<Exercise>,
    val NEW_CONTROLER: Boolean = false
) {
}