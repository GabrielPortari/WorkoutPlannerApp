package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Workout

class ExerciseRepository private constructor(context: Context) {

    private val database = WorkoutDatabase(context)

    companion object {
        private lateinit var repository: ExerciseRepository
        fun getInstance(context: Context): ExerciseRepository {
            if (!Companion::repository.isInitialized) {
                repository = ExerciseRepository(context)
            }
            return repository
        }
    }

    fun insert(workoutId: Int, exercise: Exercise): Boolean{
        return try{
            val db = database.writableDatabase

            val workout = workoutId
            val name = exercise.name
            val description = exercise.description
            val repCount = exercise.repCount

            val values = ContentValues()
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, workoutId)
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, exercise.name)
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, exercise.description)
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, exercise.repCount)

            db.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, values)

            true
        }catch(e : Exception){
            false
        }
    }

    fun update(exercise: Exercise) : Boolean{
        return try {
            val db = database.writableDatabase

            val name = exercise.name
            val description = exercise.description
            val repCount = exercise.repCount

            val values = ContentValues()

            val selection = MyConstants.DATABASE.EXERCISE_COLUMNS.ID + " = ?"
            val args = arrayOf(exercise.exerciseId.toString())
            db.update(MyConstants.DATABASE.EXERCISE_TABLE_NAME, values, selection, args)

            true
        }catch (e: Exception){
            false
        }
    }

    fun delete(id: Int): Boolean{
        return try {
            val db = database.writableDatabase

            val selection = MyConstants.DATABASE.EXERCISE_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(MyConstants.DATABASE.EXERCISE_TABLE_NAME, selection, args)

            true
        }catch (e: Exception){
            false
        }
    }

    fun getExercise(id: Int): Exercise?{
        var exercise: Exercise? = null

        try{
            val db = database.readableDatabase

            /*o que será recuperado*/
            val projection = arrayOf(
                MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID,
                MyConstants.DATABASE.EXERCISE_COLUMNS.ID,
                MyConstants.DATABASE.EXERCISE_COLUMNS.NAME,
                MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION,
                MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT
            )

            val selection = MyConstants.DATABASE.EXERCISE_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                MyConstants.DATABASE.EXERCISE_TABLE_NAME,
                projection, selection, args, null, null, null
            )

            if(cursor != null && cursor.count > 0){
                while(cursor.moveToNext()){
                    val workoutId = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID))
                    val name = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME))
                    val description = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION))
                    val repCount = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT))

                    exercise = Exercise(id, workoutId, name, description, repCount)

                }
            }

            cursor.close()

        }catch (e: Exception){
            return exercise
        }

        return exercise
    }

    fun getAllFromWorkout(idWorkout: Int): List<Exercise>{
        val list = mutableListOf<Exercise>()

        try{
            val db = database.readableDatabase

            /*o que será recuperado*/
            val projection = arrayOf(
                MyConstants.DATABASE.EXERCISE_COLUMNS.ID,
                MyConstants.DATABASE.EXERCISE_COLUMNS.NAME,
                MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION,
                MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT
            )

            val selection = MyConstants.DATABASE.WORKOUT_COLUMNS.ID + " = ?"
            val selectionArgs = arrayOf(idWorkout.toString())

            val cursor = db.query(
                MyConstants.DATABASE.WORKOUT_TABLE_NAME,
                projection, selection, selectionArgs, null, null, null
            )

            while(cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.ID))
                val name = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME))
                val description = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION))
                val repCount = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT))


                val exercise = Exercise(id, idWorkout, name, description, repCount)

                list.add(exercise)
            }

            cursor.close()

        }catch (e: Exception){
            return list
        }
        return list
    }
}