package com.gabrielportari.workoutplannerapp.service.repository

import com.gabrielportari.workoutplannerapp.service.model.Workout

class Data {
    companion object WorkoutsData{
        val workouts = generateWorkouts() + Workout(0, "", "", emptyList(), true)
    }
}

private fun generateWorkouts(): List<Workout> {

    val workout1 = Workout(1, "Treino de peito", "Treino focado em peito, deltóides laterais e tríceps", emptyList())
    val workout2 = Workout(2, "Treino de costas", "Treino focado em costas, bíceps, trapézio e abdominais", emptyList())
    val workout3 = Workout(3, "Treino de perna", "Treino focado em quadríceps e posteriores", emptyList())
    val workout4 = Workout(4, "Treino de braço", "Treino focado em bíceps, tríceps e antebraço", emptyList())
    val workout5 = Workout(5, "Treino de ombro", "Treino focado em todas as cabeças do ombro", emptyList())

    return listOf(workout1, workout2, workout3, workout4, workout5)

}