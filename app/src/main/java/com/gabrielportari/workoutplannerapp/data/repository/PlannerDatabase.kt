package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants

class PlannerDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "workout_db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {

        /* CRIAÇÃO DAS TABELAS */
        val sqlUserName = "CREATE TABLE " + MyConstants.DATABASE.USER_TABLE + "(" +
                MyConstants.DATABASE.USER_COLUMNS.ID + " integer primary key, " +
                MyConstants.DATABASE.USER_COLUMNS.NAME + " text," +
                MyConstants.DATABASE.USER_COLUMNS.ACTIVE_WEEK + " integer);"

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
                MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER + " integer);"

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

        db?.execSQL(sqlUserName)
        db?.execSQL(sqlWeekTable)
        db?.execSQL(sqlWorkoutTable)
        db?.execSQL(sqlExerciseTable)

        /* INSERÇÃO DO BOTÃO DE ADICIONAR TREINO */
        val values = ContentValues()
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.ID, 0)
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, "ADD WORKOUT BUTTON")
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, "THERE IS NOTHING TO SHOW")
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_TRUE)
        db?.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, values)

        /* INSERÇÃO DOS BOTÃO DE ADICIONAR SEMANA */
        val weekValues = ContentValues()
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.ID, 0)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.NAME, "ADD WEEK BUTTON")
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION, "THERE IS NOTHING TO SHOW")
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_TRUE)
        db?.insert(MyConstants.DATABASE.WEEK_TABLE_NAME, null, weekValues)

        /*INSERÇÃO DO USUÁRIO PADRÃO*/
        val userValues = ContentValues()
        userValues.put(MyConstants.DATABASE.USER_COLUMNS.ID, MyConstants.USER_ID.ID)
        userValues.put(MyConstants.DATABASE.USER_COLUMNS.NAME, "User")
        userValues.put(MyConstants.DATABASE.USER_COLUMNS.ACTIVE_WEEK, 0)
        db?.insert(MyConstants.DATABASE.USER_TABLE, null, userValues)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}