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

class ManageWeekViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WeekDAO.getInstance(application.applicationContext)
    private val userDAO = UserDAO.getInstance(application.applicationContext)
    private val resources = application.resources

    private val _weekList = MutableLiveData<List<Week>>()
    val weekList: LiveData<List<Week>> get() = _weekList

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _createValidation = MutableLiveData<Validation>()
    val createValidation: LiveData<Validation> get() = _createValidation

    private val _deleteValidation = MutableLiveData<Validation>()
    val deleteValidation: LiveData<Validation> get() = _deleteValidation

    fun listWeeks(){
        _weekList.value = repository.getAll()
    }

    fun loadUser(){
        _user.value = userDAO.get(MyConstants.USER_ID.ID)
    }

    fun createWeek(week: Week){
        if(repository.insert(week)){
            listWeeks()
            _createValidation.value = Validation()
        }else{
            _createValidation.value = Validation(resources.getString(R.string.failure_create_week))
        }
    }

    fun deleteWeek(id: Int){
        if(repository.delete(id)){
            listWeeks()
            _deleteValidation.value = Validation()
        }else{
            _deleteValidation.value = Validation(resources.getString(R.string.failure_delete_week))
        }
    }
}