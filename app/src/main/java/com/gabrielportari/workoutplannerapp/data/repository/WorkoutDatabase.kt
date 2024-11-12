package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants

class WorkoutDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "workout_db"
        private const val DATABASE_VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase?) {
        /* CRIAÇÃO DAS TABELAS */
        val sqlWorkoutTable = "CREATE TABLE " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" +
                MyConstants.DATABASE.WORKOUT_COLUMNS.ID + " integer primary key autoincrement, " +
                MyConstants.DATABASE.WORKOUT_COLUMNS.NAME + " text, " +
                MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION + " text, " +
                MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER + " boolean);"


        val sqlExerciseTable = "CREATE TABLE " + MyConstants.DATABASE.EXERCISE_TABLE_NAME + "(" +
                MyConstants.DATABASE.EXERCISE_COLUMNS.ID + " integer primary key autoincrement, " +
                MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID + " integer, " +
                MyConstants.DATABASE.EXERCISE_COLUMNS.NAME + " text, " +
                MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION + " text, " +
                MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT + " text, " +
                "FOREIGN KEY (" + MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID + ") REFERENCES " + MyConstants.DATABASE.WORKOUT_TABLE_NAME + "(" + MyConstants.DATABASE.WORKOUT_COLUMNS.ID + ")" +
                ");"

        db?.execSQL(sqlWorkoutTable)
        db?.execSQL(sqlExerciseTable)

        /* INSERÇÃO DOS BOTÃO DE ADICIONAR TREINO */
        val values = ContentValues()
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, "ADD WORKOUT BUTTON")
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, "THERE IS NOTHING TO SHOW")
        values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER, true)

        db?.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, values)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}