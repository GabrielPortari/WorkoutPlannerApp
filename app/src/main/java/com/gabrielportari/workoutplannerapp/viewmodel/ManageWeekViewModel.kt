package com.gabrielportari.workoutplannerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielportari.workoutplannerapp.data.model.Validation
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.data.repository.WeekRepository

class ManageWeekViewModel(application: Application) : AndroidViewModel(application) {

    //TODO -- IMPLEMENTAR REPOSITORY
    private val repository = WeekRepository.getInstance(application.applicationContext)

    private val _weekList = MutableLiveData<List<Week>>()
    val weekList: LiveData<List<Week>> get() = _weekList

    private val _validation = MutableLiveData<Validation>()
    val validation: LiveData<Validation> get() = _validation

    fun listWeeks(){
        _weekList.value = repository.getAll()
    }

    fun deleteWeek(id: Int){
        if(repository.delete(id)){
            listWeeks()
            _validation.value = Validation()
        }else{
            _validation.value = Validation("Falha ao deletar treino")
        }
    }
}