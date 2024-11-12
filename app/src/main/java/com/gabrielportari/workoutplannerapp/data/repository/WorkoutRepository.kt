package com.gabrielportari.workoutplannerapp.data.repository

import android.widget.Toast
import com.gabrielportari.workoutplannerapp.data.model.Workout

class WorkoutRepository {

    var workouts = arrayListOf<Workout>()
    private val addWorkoutItem: Workout = Workout(0, "", "", emptyList(), true)
    init {
        workouts.add(addWorkoutItem)
        workouts.addAll(generateWorkouts())
    }


    fun list(): ArrayList<Workout> {
        return workouts
    }

    fun addWorkout(workout: Workout) : String{
        workouts.add(workout)
        return "Treino adicionado com sucesso!"
    }

    fun deleteWorkout(id: Int) : String{
        if(getWorkout(id) == null){
            return "Treino não encontrado!"
        }else {
            workouts.remove(getWorkout(id))
            return "Treino deletado com sucesso!"
        }
    }

    fun getWorkout(id: Int) : Workout?{
        var workout : Workout? = null
        workouts.forEach {
            if(it.idWorkout == id){
                workout = it
            }
        }
        return workout
    }

    fun getAll() : ArrayList<Workout>{
        return workouts
    }

    fun editWorkout(workout: Workout) : String{
        //workouts = workouts-getWorkout(workout.idWorkout)
        //workouts = workouts+(workout)
        return "Treino editado com sucesso!"
    }
}


private fun generateWorkouts(): ArrayList<Workout> {
    val workout1 = Workout(1, "Treino de peito", "Treino focado em peito, deltóides laterais e tríceps", emptyList())
    val workout2 = Workout(2, "Treino de costas", "Treino focado em costas, bíceps, trapézio e abdominais", emptyList())
    val workout3 = Workout(3, "Treino de perna", "Treino focado em quadríceps e posteriores", emptyList())
    val workout4 = Workout(4, "Treino de braço", "Treino focado em bíceps, tríceps e antebraço", emptyList())
    val workout5 = Workout(5, "Treino de ombro", "Treino focado em todas as cabeças do ombro", emptyList())

    return arrayListOf(workout1, workout2, workout3, workout4, workout5)

}