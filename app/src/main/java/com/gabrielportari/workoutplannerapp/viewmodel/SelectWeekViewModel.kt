package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.data.repository.UserRepository
import com.gabrielportari.workoutplannerapp.data.repository.WeekRepository

class SelectWeekViewModel(application: Application) : AndroidViewModel(application) {
    private val weekRepository = WeekRepository.getInstance(application.applicationContext)
    private val userRepository = UserRepository.getInstance(application.applicationContext)

    private val _weeks = MutableLiveData<List<Week>>()
    val weeks: LiveData<List<Week>> get() = _weeks

    private val _validation = MutableLiveData<Validation>()
    val validation: LiveData<Validation> get() = _validation

    fun list(){
        _weeks.value = weekRepository.getAllExceptButton()
    }

    fun selectWeek(id: Int){
        if(userRepository.selectWeek(id)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation("Ocorreu um erro")
        }
    }
}