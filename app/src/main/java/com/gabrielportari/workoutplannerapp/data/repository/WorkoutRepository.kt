package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Workout

class WorkoutRepository private constructor(context: Context){

    private val database = WorkoutDatabase(context)
    private val exerciseRepository = ExerciseRepository.getInstance(context)

    /*Implementação do singleton*/
    companion object {
        private lateinit var repository: WorkoutRepository
        fun getInstance(context: Context): WorkoutRepository {
            if (!Companion::repository.isInitialized) {
                repository = WorkoutRepository(context)
            }
            return repository
        }
    }

    fun insert(workout: Workout): Boolean{
        return try{
            val db = database.writableDatabase

            val name = workout.name
            val description = workout.description
            val controller = workout.controller

            val values = ContentValues()
            values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, name)
            values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, description)
            values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER, controller)

            val rowId = db.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, values)

            /* Inserção do botão de adicionar exercícios */
            val exerciseValues = ContentValues()
            exerciseValues.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, rowId)
            exerciseValues.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "ADD EXERCISE BUTTON")
            exerciseValues.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "THERE IS NOTHING TO SHOW")
            exerciseValues.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "THERE IS NOTHING TO SHOW")
            exerciseValues.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_TRUE)
            db.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues)

            true
        }catch(e : Exception){
            false
        }
    }

    fun update(workout: Workout): Boolean{
        return try {
            val db = database.writableDatabase

            val workoutId = workout.id
            val name = workout.name
            val description = workout.description
            val values = ContentValues()
            values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.ID, workoutId)
            values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, name)
            values.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, description)


            val selection = MyConstants.DATABASE.WORKOUT_COLUMNS.ID + " = ?"
            val args = arrayOf(workout.id.toString())
            db.update(MyConstants.DATABASE.WORKOUT_TABLE_NAME, values, selection, args)

            true
        }catch (e: Exception){
            false
        }
    }

    fun delete(id: Int): Boolean{
        return try {
            val db = database.writableDatabase

            val selection = MyConstants.DATABASE.WORKOUT_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(MyConstants.DATABASE.WORKOUT_TABLE_NAME, selection, args)

            true
        }catch (e: Exception){
            false
        }
    }

    fun get(id: Int): Workout?{
        var workout: Workout? = null
        var exercises: List<Exercise>?
        try{
            val db = database.readableDatabase

            /*o que será recuperado*/
            val projection = arrayOf(
                MyConstants.DATABASE.WORKOUT_COLUMNS.ID,
                MyConstants.DATABASE.WORKOUT_COLUMNS.NAME,
                MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION
            )

            val selection = MyConstants.DATABASE.WORKOUT_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                MyConstants.DATABASE.WORKOUT_TABLE_NAME,
                projection, selection, args, null, null, null
            )

            if(cursor != null && cursor.count > 0){
                while(cursor.moveToNext()){
                    val name = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME))
                    val description = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION))
                    //get workout exercises
                    exercises = exerciseRepository.getAllFromWorkout(id)
                    workout = Workout(id, name, description, exercises, MyConstants.CONTROLLER.CONTROLLER_FALSE)

                }
            }

            cursor.close()

        }catch (e: Exception){
            return workout
        }

        return workout
    }

    fun getAll(): List<Workout>{
        val list = mutableListOf<Workout>()

        try{
            val db = database.readableDatabase

            /*o que será recuperado*/
            val projection = arrayOf(
                MyConstants.DATABASE.WORKOUT_COLUMNS.ID,
                MyConstants.DATABASE.WORKOUT_COLUMNS.NAME,
                MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION,
                MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER
            )

            val cursor = db.query(
                MyConstants.DATABASE.WORKOUT_TABLE_NAME, projection,
                null, null, null, null, null
            )

            while(cursor.moveToNext()){
                val id =
                    cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WORKOUT_COLUMNS.ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME))
                val description =
                    cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION))
                val controller =
                    cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER))

                // recuperar os exercícios
                // recuperarExercise() - retorna list de exercicio
                //val exercises = exerciseRepository.getAllFromWorkout(id)
                val exercises = emptyList<Exercise>()
                val workout = Workout(id, name, description, exercises, controller)
                list.add(workout)
            }

            cursor.close()

        }catch (e: Exception){
            return list.reversed()
        }

        return list.reversed()
    }

}