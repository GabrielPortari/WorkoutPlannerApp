package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.ExerciseRepository
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class WorkoutFormViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutRepository = WorkoutRepository.getInstance(application.applicationContext)
    private val exerciseRepository = ExerciseRepository.getInstance(application.applicationContext)

    private val _validation = MutableLiveData<Validation>()
    val validation: LiveData<Validation> get() = _validation

    private val _workout = MutableLiveData<Workout>()
    val workout: LiveData<Workout> get() = _workout

    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>> get() = _exerciseList

    private val _deleteExercise = MutableLiveData<Validation>()
    val deleteExercise: LiveData<Validation> get() = _deleteExercise



    fun loadWorkout(id: Int){
        if(workoutRepository.get(id) != null){
            _workout.value = workoutRepository.get(id)
        }else{
            _validation.value = Validation("Erro ao carregar treino")
        }
    }

    fun updateWorkout(workout: Workout){
        if(workoutRepository.update(workout)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation("Erro ao editar treino")
        }
    }

    fun deleteExercise(id: Int){
        if(exerciseRepository.delete(id)){
            _deleteExercise.value = Validation()
        }else{
            _deleteExercise.value = Validation("Erro ao deletar exercicio")
        }
    }

    fun listExercises(id: Int){
        _exerciseList.value = exerciseRepository.getAllFromWorkout(id)
    }


}