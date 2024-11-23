package com.gabrielportari.workoutplannerapp.data.model

import com.gabrielportari.workoutplannerapp.data.constants.MyConstants

data class Workout(
    val id: Int,
    val name: String,
    val description: String,
    val exercises: List<Exercise>,
    val controller: Int
) {
}