package com.gabrielportari.workoutplannerapp.data.repository

import com.gabrielportari.workoutplannerapp.data.model.Workout

class WorkoutRepository {

    private val addWorkoutItem: Workout = Workout(0, "", "", emptyList(), true)
    private var workouts: List<Workout> = generateWorkouts()

    fun list(): List<Workout> {
        /*mantém o item de adicionar ao final da lista*/
        workouts = workouts-(addWorkoutItem)
        workouts = workouts+(addWorkoutItem)

        return workouts
    }

    fun addWorkout() : String{
        return "Treino adicionado com sucesso!"
    }

    fun deleteWorkout() : String{
        return "Treino deletado com sucesso!"
    }

    fun getWorkout(id: Int) : Workout{
        return workouts[id]
    }

    fun editWorkout() : String{
        return "Treino editado com sucesso!"
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