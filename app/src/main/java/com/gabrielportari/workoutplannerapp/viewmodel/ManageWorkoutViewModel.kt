package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.ExerciseRepository
import com.gabrielportari.workoutplannerapp.data.repository.WeekRepository
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class ManageWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutRepository = WorkoutRepository.getInstance(application.applicationContext)
    private val weekRepository = WeekRepository.getInstance(application.applicationContext)
    private val exerciseRepository = ExerciseRepository.getInstance(application.applicationContext)

    private val resources = application.resources

    private val _workoutList = MutableLiveData<List<Workout>>()
    val workoutList: LiveData<List<Workout>> get() = _workoutList

    private val _createValidation = MutableLiveData<Validation>()
    val createValidation: LiveData<Validation> get() = _createValidation

    private val _deleteValidation = MutableLiveData<Validation>()
    val deleteValidation: LiveData<Validation> get() = _deleteValidation

    fun listWorkouts(){
        _workoutList.value = workoutRepository.getAll()
    }

    fun createWorkout(workout: Workout){
        if(workoutRepository.insert(workout)){
            listWorkouts()
            _createValidation.value = Validation()
        }else{
            _createValidation.value = Validation(resources.getString(R.string.failure_create_workout))
        }
    }

    fun deleteWorkout(id: Int){
        if(workoutRepository.delete(id)){
            exerciseRepository.workoutDeleted(id)
            weekRepository.workoutDeleted(id)
            listWorkouts()
            _deleteValidation.value = Validation()
        }else{
            _deleteValidation.value = Validation(resources.getString(R.string.failure_delete_workout))
        }
    }


}