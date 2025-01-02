package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WeekRepository
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class SelectWorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val workoutRepository = WorkoutRepository.getInstance(application.applicationContext)
    private val weekRepository = WeekRepository.getInstance(application.applicationContext)

    private val _workouts = MutableLiveData<List<Workout>>()
    val workouts: LiveData<List<Workout>> get() = _workouts

    private val _validation = MutableLiveData<Validation>()
    val validation: LiveData<Validation> get() = _validation

    fun list(){
        _workouts.value = workoutRepository.getAllExceptButton()
    }

    fun addWorkout(day: String, weekId: Int, id: Int){
        if(weekRepository.insertWorkoutDay(day, weekId, id)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation(R.string.failure_default.toString())
        }
    }
}