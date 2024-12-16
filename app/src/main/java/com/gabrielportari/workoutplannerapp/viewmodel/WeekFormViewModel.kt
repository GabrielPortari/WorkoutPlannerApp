package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.model.WorkoutDay
import com.gabrielportari.workoutplannerapp.data.repository.WeekRepository

class WeekFormViewModel(application: Application) : AndroidViewModel(application) {
    private val weekRepository = WeekRepository.getInstance(application.applicationContext)

    private val workoutDays = mutableListOf<WorkoutDay>()

    private val _validation = MutableLiveData<Validation>()
    val validation: LiveData<Validation> get() = _validation

    private val _delValidation = MutableLiveData<Validation>()
    val delValidation: LiveData<Validation> get() = _delValidation

    private val _week = MutableLiveData<Week>()
    val week: LiveData<Week> get() = _week

    private val _workouts = MutableLiveData<List<WorkoutDay>>()
    val workouts: LiveData<List<WorkoutDay>> get() = _workouts


    fun loadWeek(id: Int){
        if(weekRepository.get(id) != null){
            _week.value = weekRepository.get(id)

            workoutDays.clear()
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.SUNDAY, "Domingo", _week.value?.workoutDaySunday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.MONDAY, "Segunda-Feira", _week.value?.workoutdDayMonday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.TUESDAY, "Terça-Feira", _week.value?.workoutDayTuesday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.WEDNESDAY, "Quarta-Feira", _week.value?.workoutDayWednesday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.THURSDAY, "Quinta-Feira", _week.value?.workoutdDayThursday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.FRIDAY, "Sexta-Feira", _week.value?.workoutDayFriday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.SATURDAY, "Sabado", _week.value?.workoutdDaySaturday))

            _workouts.value = workoutDays

        }else{
            _validation.value = Validation("Erro ao carregar treino")
        }
    }

    fun update(name: String, description: String, id: Int){
        val workout = Workout(0, "", "", emptyList(), 1)

        val week = Week(id, name, description,
            workout, workout, workout, workout, workout, workout, workout
            , MyConstants.CONTROLLER.CONTROLLER_TRUE)

        if(weekRepository.update(week)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation("Erro ao atualizar treino")
        }
    }

    fun deleteWorkout(id: Int, day: String){
        val week = weekRepository.get(id)
        if(week != null) {
            if (weekRepository.deleteWorkoutDay(week, day)) {
                _delValidation.value = Validation()
            } else {
                _delValidation.value = Validation("Erro ao deletar treino")
            }
        }else{
            _delValidation.value = Validation("Ocorreu um erro")
        }
    }
}