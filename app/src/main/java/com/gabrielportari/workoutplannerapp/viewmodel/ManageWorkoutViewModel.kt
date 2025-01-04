package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class ManageWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository.getInstance(application.applicationContext)
    private val resources = application.resources

    private val _workoutList = MutableLiveData<List<Workout>>()
    val workoutList: LiveData<List<Workout>> get() = _workoutList

    private val _createValidation = MutableLiveData<Validation>()
    val createValidation: LiveData<Validation> get() = _createValidation

    private val _deleteValidation = MutableLiveData<Validation>()
    val deleteValidation: LiveData<Validation> get() = _deleteValidation

    fun listWorkouts(){
        _workoutList.value = repository.getAll()
    }

    fun createWorkout(workout: Workout){
        if(repository.insert(workout)){
            listWorkouts()
            _createValidation.value = Validation()
        }else{
            _createValidation.value = Validation(resources.getString(R.string.failure_create_workout))
        }
    }

    fun deleteWorkout(id: Int){
        if(repository.delete(id)){
            listWorkouts()
            _deleteValidation.value = Validation()
        }else{
            _deleteValidation.value = Validation(resources.getString(R.string.failure_delete_workout))
        }
    }


}