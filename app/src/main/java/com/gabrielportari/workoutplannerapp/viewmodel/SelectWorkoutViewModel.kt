package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WeekDAO
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutDAO

class SelectWorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val workoutDAO = WorkoutDAO.getInstance(application.applicationContext)
    private val weekDAO = WeekDAO.getInstance(application.applicationContext)
    private val resources = application.resources

    private val _workouts = MutableLiveData<List<Workout>>()
    val workouts: LiveData<List<Workout>> get() = _workouts

    private val _validation = MutableLiveData<Validation>()
    val validation: LiveData<Validation> get() = _validation

    fun list(){
        _workouts.value = workoutDAO.getAllExceptController()
    }

    fun addWorkout(day: String, weekId: Int, id: Int){
        if(weekDAO.insertWorkoutDay(day, weekId, id)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation(resources.getString(R.string.failure_default))
        }
    }
}