package com.gabrielportari.workoutplannerapp.data.model

class Validation(message: String = "") {
    private var status: Boolean = true
    private var validationMessage: String = ""

    init{
        if(message != ""){
            status = false
            validationMessage = message
        }
    }

    fun status(): Boolean{
        return status
    }

    fun message(): String{
        return validationMessage
    }
}