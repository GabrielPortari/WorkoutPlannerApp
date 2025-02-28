package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.dao.IWorkoutDAO

class WorkoutDAO private constructor(context: Context) : IWorkoutDAO {

    private val database = PlannerDatabase(context)
    private val exerciseDAO = ExerciseDAO.getInstance(context)

    /*Implementação do singleton*/
    companion object {
        private lateinit var repository: WorkoutDAO
        fun getInstance(context: Context): WorkoutDAO {
            if (!Companion::repository.isInitialized) {
                repository = WorkoutDAO(context)
            }
            return repository
        }
    }

    override fun insert(workout: Workout): Boolean{
        val db = database.writableDatabase

        return try{
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
        }finally {
            db.close()
        }
    }

    override fun update(workout: Workout): Boolean{
        val db = database.writableDatabase

        return try {
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
        }finally {
            db.close()
        }
    }

    override fun delete(id: Int): Boolean{
        val db = database.writableDatabase

        return try {
            val selection = MyConstants.DATABASE.WORKOUT_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(MyConstants.DATABASE.WORKOUT_TABLE_NAME, selection, args)

            true
        }catch (e: Exception){
            false
        }finally {
            db.close()
        }
    }

    override fun get(id: Int): Workout?{
        var workout: Workout? = null
        var exercises: List<Exercise>?
        val db = database.readableDatabase

        try{
            /*o que será recuperado*/
            val projection = arrayOf(
                MyConstants.DATABASE.WORKOUT_COLUMNS.ID,
                MyConstants.DATABASE.WORKOUT_COLUMNS.NAME,
                MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION,
                MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER
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
                    val controller = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER))
                    //get workout exercises
                    exercises = exerciseDAO.getAllFromWorkout(id)
                    workout = Workout(id, name, description, exercises, controller)

                }
            }

            cursor.close()

        }catch (e: Exception){
            return workout
        }finally {
            db.close()
        }

        return workout
    }

    override fun getAll(): List<Workout>{
        val list = mutableListOf<Workout>()
        val db = database.readableDatabase
        try{

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
                list.add(0, workout)
            }

            cursor.close()

        }catch (e: Exception){
            return list
        }finally {
            db.close()
        }

        return list
    }

    override fun getAllExceptController(): List<Workout>{
        val list = mutableListOf<Workout>()
        val db = database.readableDatabase
        try{
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

                if(controller == MyConstants.CONTROLLER.CONTROLLER_FALSE){
                    val exercises = emptyList<Exercise>()
                    val workout = Workout(id, name, description, exercises, controller)
                    list.add(0, workout)
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