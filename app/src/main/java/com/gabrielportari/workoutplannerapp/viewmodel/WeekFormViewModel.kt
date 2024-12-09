package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.model.WorkoutDay
import com.gabrielportari.workoutplannerapp.data.repository.WeekRepository
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository

class WeekFormViewModel(application: Application) : AndroidViewModel(application) {
    private val weekRepository = WeekRepository.getInstance(application.applicationContext)
    private val workoutRepository = WorkoutRepository.getInstance(application.applicationContext)

    private val workoutDays = mutableListOf<WorkoutDay>()

    private val _validation = MutableLiveData<Validation>()
    val validation: LiveData<Validation> get() = _validation

    private val _week = MutableLiveData<Week>()
    val week: LiveData<Week> get() = _week

    private val _workouts = MutableLiveData<List<WorkoutDay>>()
    val workouts: LiveData<List<WorkoutDay>> get() = _workouts

    private val _deleteWorkout = MutableLiveData<Validation>()
    val deleteWorkout: LiveData<Validation> get() = _deleteWorkout

    fun loadWeek(id: Int){
        if(weekRepository.get(id) != null){
            _week.value = weekRepository.get(id)

            workoutDays.clear()
            workoutDays.add(WorkoutDay("Domingo", _week.value?.workoutDaySunday))
            workoutDays.add(WorkoutDay("Segunda-Feira", _week.value?.workoutdDayMonday))
            workoutDays.add(WorkoutDay("Terça-Feira", _week.value?.workoutDayTuesday))
            workoutDays.add(WorkoutDay("Quarta-Feira", _week.value?.workoutDayWednesday))
            workoutDays.add(WorkoutDay("Quinta-Feira", _week.value?.workoutdDayThursday))
            workoutDays.add(WorkoutDay("Sexta-Feira", _week.value?.workoutDayFriday))
            workoutDays.add(WorkoutDay("Sabado", _week.value?.workoutdDaySaturday))

            _workouts.value = workoutDays

            _validation.value = Validation()
        }else{
            _validation.value = Validation("Erro ao carregar treino")
        }

    }
}