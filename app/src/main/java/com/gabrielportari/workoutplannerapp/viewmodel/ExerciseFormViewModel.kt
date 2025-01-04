package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.repository.ExerciseRepository

class ExerciseFormViewModel(application: Application) : AndroidViewModel(application) {

    private val exerciseRepository = ExerciseRepository.getInstance(application.applicationContext)
    private val resources = application.resources

    private val _validation = MutableLiveData<Validation>()
    val validation: LiveData<Validation> get() = _validation

    private val _exercise = MutableLiveData<Exercise>()
    val exercise: LiveData<Exercise> get() = _exercise

    private val _exerciseLoad = MutableLiveData<Validation>()
    val exerciseLoad: LiveData<Validation> get() = _exerciseLoad


    fun createExercise(exercise: Exercise){
        if(exerciseRepository.insert(exercise)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation(resources.getString(R.string.failure_delete_exercise))
        }
    }

    fun loadExercise(id: Int) {
        if (exerciseRepository.getExercise(id) != null) {
            _exercise.value = exerciseRepository.getExercise(id)
        } else {
            _exerciseLoad.value = Validation(resources.getString(R.string.failure_load_exercise))
        }
    }

    fun updateExercise(exercise: Exercise) {
        if (exerciseRepository.update(exercise)) {
            _validation.value = Validation()
        } else {
            _validation.value = Validation(resources.getString(R.string.failure_edit_exercise))
        }
    }


}