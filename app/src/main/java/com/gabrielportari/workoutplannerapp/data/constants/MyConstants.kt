package com.gabrielportari.workoutplannerapp.data.constants

class MyConstants private constructor() {

    object CONTROLLER{
        const val CONTROLLER_FALSE: Int = 0
        const val CONTROLLER_TRUE: Int = 1
    }

    object USER_ID{
        const val ID = 0
        const val NAME = "User"
    }
    object KEY{
        const val ID_KEY = "id"
        const val DAY_KEY = "day"
        const val WORKOUT_ID_KEY = "exerciseId"
    }

    object BUTTON{
        const val ADD_BUTTON = "ADD BUTTON"
        const val ADD_BUTTON_DESC = "NO DESCRIPTION"
    }
    object WEEK_DAYS{
        const val SUNDAY: String = "sunday"
        const val MONDAY: String = "monday"
        const val TUESDAY: String = "tuesday"
        const val WEDNESDAY: String = "wednesday"
        const val THURSDAY: String = "thursday"
        const val FRIDAY: String = "friday"
        const val SATURDAY: String = "saturday"
    }

    object DATABASE{

        const val USER_TABLE = "user"
        object USER_COLUMNS{
            const val ID = "id"
            const val NAME = "name"
            const val ACTIVE_WEEK = "active_week"
        }

        const val WEEK_TABLE_NAME = "week"
        object WEEK_COLUMNS{
            const val ID = "id"
            const val NAME = "name"
            const val DESCRIPTION = "description"
            const val WEEK_WORKOUT_ID_DAY_SUNDAY = "workout_sunday"
            const val WEEK_WORKOUT_ID_DAY_MONDAY = "workout_monday"
            const val WEEK_WORKOUT_ID_DAY_TUESDAY = "workout_tuesday"
            const val WEEK_WORKOUT_ID_DAY_WEDNESDAY = "workout_wednesday"
            const val WEEK_WORKOUT_ID_DAY_THURSDAY = "workout_thursday"
            const val WEEK_WORKOUT_ID_DAY_FRIDAY = "workout_friday"
            const val WEEK_WORKOUT_ID_DAY_SATURDAY = "workout_saturday"
            const val CONTROLLER = "controller"
        }

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
            const val CONTROLLER = "controller"
        }
    }

}