package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class ManageWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val _workoutList = MutableLiveData<List<Workout>>()
    val workoutList: MutableLiveData<List<Workout>> = _workoutList

    private val _validation = MutableLiveData<String>()
    val validation: MutableLiveData<String> = _validation

    fun listWorkouts(){
        workoutList.value = WorkoutRepository().list()
    }

    fun addWorkout(workout: Workout){
        validation.value = WorkoutRepository().addWorkout(workout)
    }

    fun deleteWorkout(id: Int){
        validation.value = WorkoutRepository().deleteWorkout(id)
    }

    fun editWorkout(workout: Workout){
        validation.value = WorkoutRepository().editWorkout(workout)
    }

}