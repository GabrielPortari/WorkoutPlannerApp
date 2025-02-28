package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.repository.dao.IExerciseDAO

class ExerciseDAO private constructor(context: Context) : IExerciseDAO {

    private val database = PlannerDatabase(context)

    /* implementação do singleton */
    companion object {
        private lateinit var repository: ExerciseDAO
        fun getInstance(context: Context): ExerciseDAO {
            if (!Companion::repository.isInitialized) {
                repository = ExerciseDAO(context)
            }
            return repository
        }
    }

    override fun insert(exercise: Exercise): Boolean{
        val db = database.writableDatabase

        return try{
            val values = ContentValues()
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, exercise.workoutId)
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, exercise.name)
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, exercise.description)
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, exercise.repCount)
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, exercise.controller)

            db.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, values)

            true
        }catch(e : Exception){
            false
        }finally {
            db.close()
        }
    }

    override fun update(exercise: Exercise) : Boolean{
        val db = database.writableDatabase

        return try {
            val name = exercise.name
            val description = exercise.description
            val repCount = exercise.repCount

            val values = ContentValues()
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.ID, exercise.id)
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, name)
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, description)
            values.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, repCount)

            val selection = MyConstants.DATABASE.EXERCISE_COLUMNS.ID + " = ?"
            val args = arrayOf(exercise.id.toString())
            db.update(MyConstants.DATABASE.EXERCISE_TABLE_NAME, values, selection, args)

            true
        }catch (e: Exception){
            false
        }finally {
            db.close()
        }
    }

    override fun delete(id: Int): Boolean{
        val db = database.writableDatabase

        return try {
            val selection = MyConstants.DATABASE.EXERCISE_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(MyConstants.DATABASE.EXERCISE_TABLE_NAME, selection, args)

            true
        }catch (e: Exception){
            false
        }finally {
            db.close()
        }
    }

    override fun deleteWorkout(id: Int): Boolean{
        val db = database.writableDatabase

        return try {
            val selection = MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(MyConstants.DATABASE.EXERCISE_TABLE_NAME, selection, args)

            true
        }catch (e: Exception){
            false
        }finally {
            db.close()
        }
    }

    override fun getExercise(id: Int): Exercise?{
        var exercise: Exercise? = null
        val db = database.readableDatabase

        try{
            val projection = arrayOf(
                MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID,
                MyConstants.DATABASE.EXERCISE_COLUMNS.ID,
                MyConstants.DATABASE.EXERCISE_COLUMNS.NAME,
                MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION,
                MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT,
                MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER
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
                    val controller = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER))

                    exercise = Exercise(id, workoutId, name, description, repCount, controller)

                }
            }

            cursor.close()

        }catch (e: Exception){
            return exercise
        }finally {
            db.close()
        }

        return exercise
    }

    override fun getAllFromWorkout(idWorkout: Int): List<Exercise>{
        val list = mutableListOf<Exercise>()

        val db = database.readableDatabase
        try{
            val projection = arrayOf(
                MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID,
                MyConstants.DATABASE.EXERCISE_COLUMNS.ID,
                MyConstants.DATABASE.EXERCISE_COLUMNS.NAME,
                MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION,
                MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT,
                MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER
            )

            val selection = MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID + " = ?"
            val selectionArgs = arrayOf(idWorkout.toString())

            val cursor = db.query(
                MyConstants.DATABASE.EXERCISE_TABLE_NAME,
                projection, selection, selectionArgs,
                null, null, null
            )

            while(cursor.moveToNext()){

                val id = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.ID))
                val name = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME))
                val description = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION))
                val repCount = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT))
                val controller = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER))

                val exercise = Exercise(id, idWorkout, name, description, repCount, controller)
                list.add(0, exercise)
            }

            cursor.close()

        }catch (e: Exception){
            return list
        }finally {
            db.close()
        }
        return list
    }

    override fun getAllFromWorkoutExceptButton(idWorkout: Int): List<Exercise>{
        val list = mutableListOf<Exercise>()
        val db = database.readableDatabase

        try{
            /*o que será recuperado*/
            val projection = arrayOf(
                MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID,
                MyConstants.DATABASE.EXERCISE_COLUMNS.ID,
                MyConstants.DATABASE.EXERCISE_COLUMNS.NAME,
                MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION,
                MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT,
                MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER
            )

            val selection = MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID + " = ?"
            val selectionArgs = arrayOf(idWorkout.toString())

            val cursor = db.query(
                MyConstants.DATABASE.EXERCISE_TABLE_NAME,
                projection, selection, selectionArgs,
                null, null, null
            )

            while(cursor.moveToNext()){

                val id = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.ID))
                val name = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME))
                val description = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION))
                val repCount = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT))
                val controller = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER))

                if(controller == MyConstants.CONTROLLER.CONTROLLER_FALSE) {
                    val exercise = Exercise(id, idWorkout, name, description, repCount, controller)
                    list.add(0, exercise)
                }
            }

            cursor.close()

        }catch (e: Exception){
            return list
        }finally {
            db.close()
        }

        return list
    }
}