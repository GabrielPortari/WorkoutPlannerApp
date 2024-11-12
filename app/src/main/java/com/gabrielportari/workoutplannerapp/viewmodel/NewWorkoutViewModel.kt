package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class NewWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository()

    private val _create = MutableLiveData<String>()
    val create: MutableLiveData<String> = _create

    fun createWorkout(workout: Workout){
        create.value = repository.addWorkout(workout)
    }
}