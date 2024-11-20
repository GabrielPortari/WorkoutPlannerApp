package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class NewWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository.getInstance(application.applicationContext)

    private val _validation = MutableLiveData<String>()
    val validation: MutableLiveData<String> = _validation

    fun createWorkout(workout: Workout){
        if(repository.insert(workout)){
            _validation.value = "Workout created successfully"
        }else{
            _validation.value = "Something went wrong"

        }
    }
}