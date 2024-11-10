package com.gabrielportari.workoutplannerapp.service.model

class ValidationModel(message: String) {
    private var status: Boolean = true
    private var validationMessage: String = ""

    fun status() = status
    fun message() = validationMessage

}