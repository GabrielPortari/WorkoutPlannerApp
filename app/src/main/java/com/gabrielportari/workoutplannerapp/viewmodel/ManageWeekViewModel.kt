package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.data.repository.WeekRepository

class ManageWeekViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WeekRepository.getInstance(application.applicationContext)

    private val _weekList = MutableLiveData<List<Week>>()
    val weekList: LiveData<List<Week>> get() = _weekList

    private val _createValidation = MutableLiveData<Validation>()
    val createValidation: LiveData<Validation> get() = _createValidation

    private val _deleteValidation = MutableLiveData<Validation>()
    val deleteValidation: LiveData<Validation> get() = _deleteValidation

    fun listWeeks(){
        _weekList.value = repository.getAll()
    }

    fun createWeek(week: Week){
        if(repository.insert(week)){
            listWeeks()
            _createValidation.value = Validation()
        }else{
            _createValidation.value = Validation("Falha ao inserir semana")
        }
    }

    fun deleteWeek(id: Int){
        if(repository.delete(id)){
            listWeeks()
            _deleteValidation.value = Validation()
        }else{
            _deleteValidation.value = Validation("Falha ao deletar semana")
        }
    }
}