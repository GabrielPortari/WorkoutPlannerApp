package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.service.model.Workout
import com.gabrielportari.workoutplannerapp.service.repository.Data
import com.google.android.material.snackbar.Snackbar

class ManageWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val _workoutList = MutableLiveData<List<Workout>>()
    val workoutList: MutableLiveData<List<Workout>> = _workoutList

    fun listWorkouts(){
        workoutList.value = Data.workouts
    }



}