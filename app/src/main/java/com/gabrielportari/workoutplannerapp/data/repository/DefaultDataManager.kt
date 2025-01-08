package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants

class DefaultDataManager{

    private val resources = Resources.getSystem()

    fun createTables(db: SQLiteDatabase?){
        val sqlUserName = "CREATE TABLE " + MyConstants.DATABASE.USER_TABLE + "(" +
                MyConstants.DATABASE.USER_COLUMNS.ID + " integer primary key, " +
                MyConstants.DATABASE.USER_COLUMNS.NAME + " text," +
                MyConstants.DATABASE.USER_COLUMNS.ACTIVE_WEEK + " integer);"

        val sqlWorkoutTable = "CREATE TABLE " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" +
                MyConstants.DATABASE.WORKOUT_COLUMNS.ID + " integer primary key autoincrement, " +
                MyConstants.DATABASE.WORKOUT_COLUMNS.NAME + " text, " +
                MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION + " text, " +
                MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER + " integer);"


        val sqlExerciseTable = "CREATE TABLE " + MyConstants.DATABASE.EXERCISE_TABLE_NAME + "(" +
                MyConstants.DATABASE.EXERCISE_COLUMNS.ID + " integer primary key autoincrement, " +
                MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID + " integer, " +
                MyConstants.DATABASE.EXERCISE_COLUMNS.NAME + " text, " +
                MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION + " text, " +
                MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT + " text, " +
                MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER + " integer, " +
                "FOREIGN KEY (" + MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID + ") REFERENCES " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" + MyConstants.DATABASE.WORKOUT_COLUMNS.ID + ")" +
                ");"

        val sqlWeekTable = "CREATE TABLE " + MyConstants.DATABASE.WEEK_TABLE_NAME + "(" +
                MyConstants.DATABASE.WEEK_COLUMNS.ID + " integer primary key autoincrement, " +
                MyConstants.DATABASE.WEEK_COLUMNS.NAME + " text, " +
                MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION + " text, " +
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY + " integer, " +
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY + " integer, " +
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY + " integer, " +
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY + " integer, " +
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY + " integer, " +
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY + " integer, " +
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY + " integer, " +
                MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER + " integer, " +
                "FOREIGN KEY (" + MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY + ") REFERENCES " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" + MyConstants.DATABASE.WORKOUT_COLUMNS.ID + ")," +
                "FOREIGN KEY (" + MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY + ") REFERENCES " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" + MyConstants.DATABASE.WORKOUT_COLUMNS.ID + ")," +
                "FOREIGN KEY (" + MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY + ") REFERENCES " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" + MyConstants.DATABASE.WORKOUT_COLUMNS.ID + ")," +
                "FOREIGN KEY (" + MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY + ") REFERENCES " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" + MyConstants.DATABASE.WORKOUT_COLUMNS.ID + ")," +
                "FOREIGN KEY (" + MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY + ") REFERENCES " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" + MyConstants.DATABASE.WORKOUT_COLUMNS.ID + ")," +
                "FOREIGN KEY (" + MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY + ") REFERENCES " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" + MyConstants.DATABASE.WORKOUT_COLUMNS.ID + ")," +
                "FOREIGN KEY (" + MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY + ") REFERENCES " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" + MyConstants.DATABASE.WORKOUT_COLUMNS.ID + ")" +
                ");"



        db?.execSQL(sqlUserName)
        db?.execSQL(sqlWorkoutTable)
        db?.execSQL(sqlExerciseTable)
        db?.execSQL(sqlWeekTable)
    }

    fun createButtons(db: SQLiteDatabase?){
        val values = ContentValues()
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.ID, 0)
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, MyConstants.BUTTON.ADD_BUTTON)
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, MyConstants.BUTTON.ADD_BUTTON_DESC)
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_TRUE)
        db?.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, values)

        /* INSERÇÃO DOS BOTÃO DE ADICIONAR SEMANA */
        val weekValues = ContentValues()
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.ID, 0)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.NAME, MyConstants.BUTTON.ADD_BUTTON)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION, MyConstants.BUTTON.ADD_BUTTON_DESC)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_TRUE)
        db?.insert(MyConstants.DATABASE.WEEK_TABLE_NAME, null, weekValues)
    }

    private fun createDefaultWorkouts(db: SQLiteDatabase?){
        /*CRIAÇÃO DE TREINOS PADRÃO (ABC - PUSH PULL LEGS) */
        val workoutValues = ContentValues()
        workoutValues.put(MyConstants.DATABASE.WORKOUT_COLUMNS.ID, 1)
        workoutValues.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, resources.getString(R.string.A_workout))
        workoutValues.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, resources.getString(R.string.A_workout_description))
        workoutValues.put(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val workoutValues2 = ContentValues()
        workoutValues2.put(MyConstants.DATABASE.WORKOUT_COLUMNS.ID, 2)
        workoutValues2.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, resources.getString(R.string.B_workout))
        workoutValues2.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, resources.getString(R.string.B_workout_description))
        workoutValues2.put(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val workoutValues3 = ContentValues()
        workoutValues3.put(MyConstants.DATABASE.WORKOUT_COLUMNS.ID, 3)
        workoutValues3.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, resources.getString(R.string.C_workout))
        workoutValues3.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, resources.getString(R.string.C_workout_description))
        workoutValues3.put(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        db?.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, workoutValues)
        db?.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, workoutValues2)
        db?.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, workoutValues3)

    }

    private fun createDefaultChestExercises(db: SQLiteDatabase?){
        val exerciseValues1 = ContentValues()
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 1)
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.bench_press))
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.bench_press_description))
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.bench_press_sets_and_reps))
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues2 = ContentValues()
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 1)
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.inclined_bench_press))
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.inclined_bench_press_description))
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.inclined_bench_press_sets_and_reps))
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues3 = ContentValues()
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 1)
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.cable_triceps_extension))
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.cable_triceps_extension_description))
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.cable_triceps_extension_sets_and_reps))
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues4 = ContentValues()
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 1)
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.lateral_raise))
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.lateral_raise_description))
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.lateral_raise_sets_and_reps))
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues1)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues2)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues3)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues4)

    }

    private fun createDefaultBackExercises(db: SQLiteDatabase?){
        val exerciseValues1 = ContentValues()
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 2)
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.lat_pulldown))
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.lat_pulldown_description))
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.lat_pulldown_sets_and_reps))
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues2 = ContentValues()
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 2)
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.low_row))
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.low_row_description))
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.low_row_sets_and_reps))
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues3 = ContentValues()
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 2)
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.hammer_curl))
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.hammer_curl_description))
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.hammer_curl_sets_and_reps))
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues4 = ContentValues()
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 2)
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.seated_reverse_fly))
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.seated_reverse_fly_description))
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.seated_reverse_fly_sets_and_reps))
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues1)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues2)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues3)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues4)
    }

    private fun createDefaultLegsExercises(db: SQLiteDatabase?){
        val exerciseValues1 = ContentValues()
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 3)
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.squat))
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.squat_description))
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.squat_sets_and_reps))
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues2 = ContentValues()
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID,3)
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.stiff))
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.stiff_description))
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.stiff_sets_and_reps))
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues3 = ContentValues()
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 3)
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.leg_press))
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.leg_press_description))
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.leg_press_sets_and_reps))
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues4 = ContentValues()
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 3)
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, resources.getString(R.string.seated_calves))
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, resources.getString(R.string.seated_calves_description))
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, resources.getString(R.string.seated_calves_sets_and_reps))
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues1)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues2)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues3)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues4)
    }

    private fun createDefaultWeek(db: SQLiteDatabase?){
        val weekValues = ContentValues()
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.ID, 1)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.NAME, resources.getString(R.string.abc_workout))
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION, resources.getString(R.string.abc_workout_description))
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY, 0)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY, 1)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY, 0)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY, 2)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY, 0)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY, 3)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY, 0)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        db?.insert(MyConstants.DATABASE.WEEK_TABLE_NAME, null, weekValues)

    }

    private fun createDefaultUser(db: SQLiteDatabase?){
        val userValues = ContentValues()
        userValues.put(MyConstants.DATABASE.USER_COLUMNS.ID, MyConstants.USER_ID.ID)
        userValues.put(MyConstants.DATABASE.USER_COLUMNS.NAME, resources.getString(R.string.user_name))
        userValues.put(MyConstants.DATABASE.USER_COLUMNS.ACTIVE_WEEK, 1)

        db?.insert(MyConstants.DATABASE.USER_TABLE, null, userValues)
    }

    fun createDefaultData(db: SQLiteDatabase?){

        /* INSERÇÃO DOS TREINOS PADRÃO */
        createDefaultWorkouts(db)

        /* INSERÇÃO DOS EXERCICIOS PADRÃO */
        createDefaultChestExercises(db)
        createDefaultBackExercises(db)
        createDefaultLegsExercises(db)

        /* INSERÇÃO DA SEMANA PADRÃO */
        createDefaultWeek(db)

        /* INSERÇÃO DO USUÁRIO PADRÃO */
        createDefaultUser(db)

    }
}