package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.AlertDialog
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class ManageWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository.getInstance(application.applicationContext)

    private val _workoutList = MutableLiveData<List<Workout>>()
    val workoutList: MutableLiveData<List<Workout>> = _workoutList

    private val _validation = MutableLiveData<String>()
    val validation: MutableLiveData<String> = _validation

    fun listWorkouts(){
        _workoutList.value = repository.getAll()
    }

    fun deleteWorkout(id: Int){
        if(repository.delete(id)){
            listWorkouts()
            _validation.value = "Treino deletado com sucesso"
        }else{
            _validation.value = "Falha ao deletar treino"
        }
    }

    fun editWorkout(workout: Workout){

    }

}