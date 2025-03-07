package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.ExerciseDAO
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutDAO

class WorkoutFormViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutDAO = WorkoutDAO.getInstance(application.applicationContext)
    private val exerciseDAO = ExerciseDAO.getInstance(application.applicationContext)
    private val resources = application.resources

    private val _validation = MutableLiveData<Validation>()
    val validation: LiveData<Validation> get() = _validation

    private val _workout = MutableLiveData<Workout>()
    val workout: LiveData<Workout> get() = _workout

    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>> get() = _exerciseList

    private val _deleteExercise = MutableLiveData<Validation>()
    val deleteExercise: LiveData<Validation> get() = _deleteExercise



    fun loadWorkout(id: Int){
        if(workoutDAO.get(id) != null){
            _workout.value = workoutDAO.get(id)
        }else{
            _validation.value = Validation(resources.getString(R.string.failure_load_workout))
        }
    }

    fun updateWorkout(workout: Workout){
        if(workoutDAO.update(workout)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation(resources.getString(R.string.failure_edit_workout))
        }
    }

    fun deleteExercise(id: Int){
        if(exerciseDAO.delete(id)){
            _deleteExercise.value = Validation()
        }else{
            _deleteExercise.value = Validation(resources.getString(R.string.failure_delete_exercise))
        }
    }

    fun listExercises(id: Int){
        _exerciseList.value = exerciseDAO.getAllFromWorkout(id)
    }


}