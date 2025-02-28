package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.User
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.data.repository.UserDAO
import com.gabrielportari.workoutplannerapp.data.repository.WeekDAO

class SelectWeekViewModel(application: Application) : AndroidViewModel(application) {
    private val weekDAO = WeekDAO.getInstance(application.applicationContext)
    private val userDAO = UserDAO.getInstance(application.applicationContext)
    private val resources = application.resources

    private val _weeks = MutableLiveData<List<Week>>()
    val weeks: LiveData<List<Week>> get() = _weeks

    private val _validation = MutableLiveData<Validation>()
    val validation: LiveData<Validation> get() = _validation

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun list(){
        _weeks.value = weekDAO.getAllExceptController()
    }

    fun loadUser(){
        _user.value = userDAO.get(MyConstants.USER_ID.ID)
    }

    fun selectWeek(id: Int){
        if(userDAO.selectWeek(id)){
            _validation.value = Validation()
        }else{
            _validation.value = Validation(resources.getString(R.string.failure_default))
        }
    }
}