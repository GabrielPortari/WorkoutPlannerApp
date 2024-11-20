package com.gabrielportari.workoutplannerapp.data.constants

class MyConstants private constructor() {

    object CONTROLLER{
        const val CONTROLLER_FALSE: Int = 0
        const val CONTROLLER_TRUE: Int = 1
    }

    object KEY{
        const val ID_KEY = "id"
    }

    object DATABASE{

        const val USER_TABLE = "user"
        object USER_COLUMNS{
            const val ID = "id"
            const val NAME = "name"
        }

        const val WEEK_TABLE_NAME = "week"
        object WEEK_COLUMNS{
            const val ID = "id"
            const val NAME = "name"
            const val DESCRIPTION = "description"
            const val DAY_OF_WEEK = "day_of_week"
        }

        const val WORKOUT_TABLE_NAME = "workout"
        object WORKOUT_COLUMNS{
            const val ID = "id"
            const val NAME = "name"
            const val DESCRIPTION = "description"
            const val CONTROLLER = "controller"
        }

        const val WEEK_WORKOUT_TABLE_NAME = "week_workout"
        object WEEK_WORKOUT_COLUMNS{
            const val WEEK_ID = "week_id"
            const val WORKOUT_ID = "workout_id"
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