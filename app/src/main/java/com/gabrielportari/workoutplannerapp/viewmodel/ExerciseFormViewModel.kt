package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.repository.ExerciseRepository

class ExerciseFormViewModel(application: Application) : AndroidViewModel(application) {

    private val exerciseRepository = ExerciseRepository.getInstance(application.applicationContext)

    private val _validation = MutableLiveData<Validation>()
    val validation: MutableLiveData<Validation> = _validation

    private val _exercise = MutableLiveData<Exercise>()
    val exercise: MutableLiveData<Exercise> = _exercise

    private val _exerciseLoad = MutableLiveData<Validation>()
    val exerciseLoad: MutableLiveData<Validation> = _exerciseLoad


    fun createExercise(exercise: Exercise){
        if(exerciseRepository.insert(exercise)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation("Erro ao criar exercicio")
        }
    }

    fun loadExercise(id: Int) {
        if (exerciseRepository.getExercise(id) != null) {
            _exercise.value = exerciseRepository.getExercise(id)
        } else {
            _exerciseLoad.value = Validation("Erro ao carregar exercicio")
        }
    }

    fun updateExercise(exercise: Exercise) {
        if (exerciseRepository.update(exercise)) {
            _validation.value = Validation()
        } else {
            _validation.value = Validation("Erro ao editar exercicio")
        }
    }


}