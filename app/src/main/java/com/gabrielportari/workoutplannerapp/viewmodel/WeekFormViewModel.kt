package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.model.WorkoutDay
import com.gabrielportari.workoutplannerapp.data.repository.WeekDAO

class WeekFormViewModel(application: Application) : AndroidViewModel(application) {
    private val weekDAO = WeekDAO.getInstance(application.applicationContext)
    private val resources = application.resources

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
        if(weekDAO.get(id) != null){
            _week.value = weekDAO.get(id)

            workoutDays.clear()
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.SUNDAY, resources.getString(R.string.sunday), _week.value?.workoutDaySunday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.MONDAY, resources.getString(R.string.monday), _week.value?.workoutdDayMonday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.TUESDAY, resources.getString(R.string.tuesday), _week.value?.workoutDayTuesday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.WEDNESDAY, resources.getString(R.string.wednesday), _week.value?.workoutDayWednesday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.THURSDAY, resources.getString(R.string.thursday), _week.value?.workoutdDayThursday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.FRIDAY, resources.getString(R.string.friday), _week.value?.workoutDayFriday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.SATURDAY, resources.getString(R.string.saturday), _week.value?.workoutdDaySaturday))

            _workouts.value = workoutDays

        }else{
            _validation.value = Validation(resources.getString(R.string.failure_load_week))
        }
    }

    fun update(name: String, description: String, id: Int){
        val workout = Workout(0, "", "", emptyList(), 1)

        val week = Week(id, name, description,
            workout, workout, workout, workout, workout, workout, workout
            , MyConstants.CONTROLLER.CONTROLLER_TRUE)

        if(weekDAO.update(week)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation(resources.getString(R.string.failure_edit_week))
        }
    }

    fun deleteWorkout(id: Int, day: String){
        val week = weekDAO.get(id)
        if(week != null) {
            if (weekDAO.deleteWorkoutDay(week, day)) {
                _delValidation.value = Validation()
            } else {
                _delValidation.value = Validation(resources.getString(R.string.failure_delete_workout))
            }
        }else{
            _delValidation.value = Validation(resources.getString(R.string.failure_default))
        }
    }
}