package com.gabrielportari.workoutplannerapp.service.model

class Exercise(
    val idExercise: Int,
    var name: String,
    var description: String,
    var repCount: String,
    var advancedTechniques: List<String>
) {
}