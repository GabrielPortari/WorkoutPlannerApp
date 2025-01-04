package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.User
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.data.model.WorkoutDay
import com.gabrielportari.workoutplannerapp.data.repository.UserRepository
import com.gabrielportari.workoutplannerapp.data.repository.WeekRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val weekRepository = WeekRepository.getInstance(application.applicationContext)
    private val userRepository = UserRepository.getInstance(application.applicationContext)
    private val resources = application.resources

    private val workoutDays = mutableListOf<WorkoutDay>()
    private val _workouts = MutableLiveData<List<WorkoutDay>>()

    val workouts: LiveData<List<WorkoutDay>> get() = _workouts
    private val _week = MutableLiveData<Week>()
    val week: LiveData<Week> get() = _week
    private val _weekName = MutableLiveData<String>()

    val weekName: LiveData<String> get() = _weekName
    private val _validation = MutableLiveData<Validation>()

    val validation: LiveData<Validation> get() = _validation
    private val _user = MutableLiveData<User>()

    val user: LiveData<User> get() = _user

    fun loadUser(){
        _user.value = userRepository.get(MyConstants.USER_ID.ID)
    }

    fun loadWeek(id: Int){
        if(weekRepository.get(id) != null){
            _week.value = weekRepository.get(id)
            _weekName.value = _week.value?.name

            workoutDays.clear()

            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.SUNDAY, resources.getString(R.string.sunday), _week.value?.workoutDaySunday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.MONDAY, resources.getString(R.string.monday), _week.value?.workoutdDayMonday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.TUESDAY, resources.getString(R.string.tuesday), _week.value?.workoutDayTuesday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.WEDNESDAY, resources.getString(R.string.wednesday), _week.value?.workoutDayWednesday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.THURSDAY, resources.getString(R.string.thursday), _week.value?.workoutdDayThursday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.FRIDAY, resources.getString(R.string.friday), _week.value?.workoutDayFriday))
            workoutDays.add(WorkoutDay(MyConstants.WEEK_DAYS.SATURDAY, resources.getString(R.string.saturday), _week.value?.workoutdDaySaturday))

            _workouts.value = workoutDays

        }
    }

    fun updateName(user: User){
        if(userRepository.update(user)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation(resources.getString(R.string.failure_update_name))
        }
    }
}