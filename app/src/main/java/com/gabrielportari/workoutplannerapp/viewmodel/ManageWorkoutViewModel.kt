package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.AlertDialog
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class ManageWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository.getInstance(application.applicationContext)

    private val _workoutList = MutableLiveData<List<Workout>>()
    val workoutList: MutableLiveData<List<Workout>> = _workoutList

    private val _validation = MutableLiveData<Validation>()
    val validation: MutableLiveData<Validation> = _validation

    fun listWorkouts(){
        _workoutList.value = repository.getAll()
    }

    fun deleteWorkout(id: Int){
        if(repository.delete(id)){
            listWorkouts()
            _validation.value = Validation()
        }else{
            _validation.value = Validation("Falha ao deletar treino")
        }
    }


}