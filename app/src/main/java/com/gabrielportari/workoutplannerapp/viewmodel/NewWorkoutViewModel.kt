package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class NewWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository.getInstance(application.applicationContext)

    private val _validation = MutableLiveData<Validation>()
    val validation: MutableLiveData<Validation> = _validation

    private val _workout = MutableLiveData<Workout>()
    val workout: MutableLiveData<Workout> = _workout
    private val _workoutLoad = MutableLiveData<Validation>()
    val workoutLoad: MutableLiveData<Validation> = _workoutLoad

    fun createWorkout(workout: Workout){
        if(repository.insert(workout)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation("Erro ao criar treino")

        }
    }

    fun loadWorkout(id: Int){
        if(repository.get(id) != null){
            _workout.value = repository.get(id)
            _workoutLoad.value = Validation()
        }else{
            _workoutLoad.value = Validation()
        }
    }

    fun updateWorkout(workout: Workout){
        if(repository.update(workout)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation("Erro ao editar treino")
        }
    }
}