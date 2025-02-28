package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.ExerciseDAO
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutDAO

class ShowWorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val workoutDAO = WorkoutDAO.getInstance(application.applicationContext)
    private val exerciseDAO = ExerciseDAO.getInstance(application.applicationContext)

    private val _workout = MutableLiveData<Workout>()
    val workout: LiveData<Workout> get() = _workout

    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>> get() = _exerciseList

    fun loadWorkout(id: Int){
        _workout.value = workoutDAO.get(id)
    }

    fun listExercises(id: Int){
        _exerciseList.value = exerciseDAO.getAllFromWorkoutExceptButton(id).reversed()
    }

}