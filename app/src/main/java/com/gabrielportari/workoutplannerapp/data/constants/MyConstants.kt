package com.gabrielportari.workoutplannerapp.data.constants

class MyConstants private constructor() {

    object DATABASE{
        const val WORKOUT_TABLE_NAME = "workout"
        object WORKOUT_COLUMNS{
            const val ID = "id"
            const val NAME = "name"
            const val DESCRIPTION = "description"
            const val CONTROLLER = "controller"

        }

        const val EXERCISE_TABLE_NAME = "exercise"
        object EXERCISE_COLUMNS{
            const val ID = "id"
            const val WORKOUT_ID = "workout_id"
            const val NAME = "name"
            const val DESCRIPTION = "description"
            const val REP_COUNT = "rep_count"
        }
    }

}