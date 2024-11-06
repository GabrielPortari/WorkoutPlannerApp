package com.gabrielportari.workoutplannerapp.service.model

class Workout(
    var name: String,
    var description: String,
    var exercises: List<Exercise>,
    val NEW_CONTROLER: Boolean = false
) {
}