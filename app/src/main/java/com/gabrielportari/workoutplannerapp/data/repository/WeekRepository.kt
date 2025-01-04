package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Week

class WeekRepository private constructor(context: Context){

    private val database = PlannerDatabase(context)
    val workoutRepository = WorkoutRepository.getInstance(context)

    companion object{
        private lateinit var repository: WeekRepository
        fun getInstance(context: Context): WeekRepository{
            if(!Companion::repository.isInitialized){
                repository = WeekRepository(context)
            }
            return repository
        }
    }

    fun insert(week: Week) : Boolean{
        return try{
            val db = database.writableDatabase

            val values = ContentValues()
            val name = week.name
            val description = week.description
            val controller = week.controller

            values.put(MyConstants.DATABASE.WEEK_COLUMNS.NAME, name)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION, description)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY, 0)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY, 0)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY, 0)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY, 0)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY, 0)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY, 0)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY, 0)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER, controller)


            db.insert(MyConstants.DATABASE.WEEK_TABLE_NAME, null, values)

            true
        }catch(e : Exception){
            false
        }
    }

    fun update(week: Week) : Boolean{
        return try {
            val db = database.writableDatabase

            val id = week.id
            val name = week.name
            val description = week.description

            val values = ContentValues()
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.ID, id)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.NAME, name)
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION, description)

            val selection = MyConstants.DATABASE.WEEK_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            db.update(MyConstants.DATABASE.WEEK_TABLE_NAME, values, selection, args)

            true
        }catch (e: Exception){
            false
        }
    }

    fun insertWorkoutDay(day: String, weekId: Int, id: Int): Boolean{
        // TODO: ALTERAR O TREINO PARA BUTTON QUANDO UM TREINO FOR EXCLUÍDO
        return try{
            val db = database.writableDatabase

            val values = ContentValues()
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.ID, weekId)

            when(day){
                MyConstants.WEEK_DAYS.SUNDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY, id)
                MyConstants.WEEK_DAYS.MONDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY, id)
                MyConstants.WEEK_DAYS.TUESDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY, id)
                MyConstants.WEEK_DAYS.WEDNESDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY, id)
                MyConstants.WEEK_DAYS.THURSDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY, id)
                MyConstants.WEEK_DAYS.FRIDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY, id)
                MyConstants.WEEK_DAYS.SATURDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY, id)
                else -> return false
            }
            val selection = MyConstants.DATABASE.WEEK_COLUMNS.ID + " = ?"
            val args = arrayOf(weekId.toString())

            db.update(MyConstants.DATABASE.WEEK_TABLE_NAME, values, selection, args)

            true
        }catch(e: Exception){
            false
        }
    }

    fun deleteWorkoutDay(week: Week, day: String) : Boolean{
        return try {
            val db = database.writableDatabase

            val values = ContentValues()
            values.put(MyConstants.DATABASE.WEEK_COLUMNS.ID, week.id)

            when(day){
                MyConstants.WEEK_DAYS.SUNDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY, 0)
                MyConstants.WEEK_DAYS.MONDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY, 0)
                MyConstants.WEEK_DAYS.TUESDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY, 0)
                MyConstants.WEEK_DAYS.WEDNESDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY, 0)
                MyConstants.WEEK_DAYS.THURSDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY, 0)
                MyConstants.WEEK_DAYS.FRIDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY, 0)
                MyConstants.WEEK_DAYS.SATURDAY -> values.put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY, 0)
                else -> return false
            }

            val selection = MyConstants.DATABASE.WEEK_COLUMNS.ID + " = ?"
            val args = arrayOf(week.id.toString())

            db.update(MyConstants.DATABASE.WEEK_TABLE_NAME, values, selection, args)


            true
        }catch (e: Exception){
            false
        }
    }

    fun delete(id: Int) : Boolean{
        return try{
            val db = database.writableDatabase

            val selection = MyConstants.DATABASE.WEEK_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(MyConstants.DATABASE.WEEK_TABLE_NAME, selection, args)

            true
        }catch (e: Exception){
            false
        }
    }

    fun get(id: Int) : Week?{
        var week: Week? = null

        try {
            val db = database.readableDatabase

            val projection = arrayOf(
                MyConstants.DATABASE.WEEK_COLUMNS.ID,
                MyConstants.DATABASE.WEEK_COLUMNS.NAME,
                MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY
            )

            val selection = MyConstants.DATABASE.WEEK_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                MyConstants.DATABASE.WEEK_TABLE_NAME,
                projection, selection, args, null, null, null
            )
            if(cursor != null && cursor.count > 0){
                while(cursor.moveToNext()){
                    val name = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.NAME))
                    val description = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION))

                    val workoutIdSunday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY))
                    val workoutIdMonday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY))
                    val workoutIdTuesday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY))
                    val workoutIdWednesday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY))
                    val workoutIdThursday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY))
                    val workoutIdFriday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY))
                    val workoutIdSaturday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY))

                    //recuperar os exercícios
                    val exercisesSunday = workoutRepository.get(workoutIdSunday)
                    val exercisesMonday = workoutRepository.get(workoutIdMonday)
                    val exercisesTuesday = workoutRepository.get(workoutIdTuesday)
                    val exercisesWednesday = workoutRepository.get(workoutIdWednesday)
                    val exercisesThursday = workoutRepository.get(workoutIdThursday)
                    val exercisesFriday = workoutRepository.get(workoutIdFriday)
                    val exercisesSaturday = workoutRepository.get(workoutIdSaturday)

                    week = Week(
                        id, name, description,
                        exercisesSunday,
                        exercisesMonday,
                        exercisesTuesday,
                        exercisesWednesday,
                        exercisesThursday,
                        exercisesFriday,
                        exercisesSaturday,
                        MyConstants.CONTROLLER.CONTROLLER_FALSE)


                }
            }
            cursor.close()

        }catch (e: Exception){
            return week
        }
        return week
    }

    fun getAll() : List<Week>{
        var week: Week? = null
        var list = mutableListOf<Week>()

        try {
            val db = database.readableDatabase

            val projection = arrayOf(
                MyConstants.DATABASE.WEEK_COLUMNS.ID,
                MyConstants.DATABASE.WEEK_COLUMNS.NAME,
                MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER
            )


            val cursor = db.query(
                MyConstants.DATABASE.WEEK_TABLE_NAME,
                projection, null, null, null, null, null
            )

            if(cursor != null && cursor.count > 0){
                while(cursor.moveToNext()){

                    val id = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.NAME))
                    val description = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION))

                    val workoutIdSunday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY))
                    val workoutIdMonday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY))
                    val workoutIdTuesday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY))
                    val workoutIdWednesday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY))
                    val workoutIdThursday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY))
                    val workoutIdFriday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY))
                    val workoutIdSaturday = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY))

                    val controller = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER))


                    //recuperar os exercícios
                    val exercisesSunday = workoutRepository.get(workoutIdSunday)
                    val exercisesMonday = workoutRepository.get(workoutIdMonday)
                    val exercisesTuesday = workoutRepository.get(workoutIdTuesday)
                    val exercisesWednesday = workoutRepository.get(workoutIdWednesday)
                    val exercisesThursday = workoutRepository.get(workoutIdThursday)
                    val exercisesFriday = workoutRepository.get(workoutIdFriday)
                    val exercisesSaturday = workoutRepository.get(workoutIdSaturday)

                    week = Week(
                        id, name, description,
                        exercisesSunday,
                        exercisesMonday,
                        exercisesTuesday,
                        exercisesWednesday,
                        exercisesThursday,
                        exercisesFriday,
                        exercisesSaturday,
                        controller)

                    list.add(0, week)


                }
            }
            cursor.close()

        }catch (e: Exception){
            return list
        }
        return list
    }

    fun getAllExceptButton() : List<Week>{
        var week: Week? = null
        var list = mutableListOf<Week>()

        try {
            val db = database.readableDatabase

            val projection = arrayOf(
                MyConstants.DATABASE.WEEK_COLUMNS.ID,
                MyConstants.DATABASE.WEEK_COLUMNS.NAME,
                MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY,
                MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER
            )


            val cursor = db.query(
                MyConstants.DATABASE.WEEK_TABLE_NAME,
                projection, null, null, null, null, null
            )

            if(cursor != null && cursor.count > 0){
                while(cursor.moveToNext()){

                    val id = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.NAME))
                    val description = cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION))
                    val controller = cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.WEEK_COLUMNS.CONTROLLER))


                    //não há necessidade de recuperar os exercicios
                    val exercisesSunday = workoutRepository.get(0)
                    val exercisesMonday = workoutRepository.get(0)
                    val exercisesTuesday = workoutRepository.get(0)
                    val exercisesWednesday = workoutRepository.get(0)
                    val exercisesThursday = workoutRepository.get(0)
                    val exercisesFriday = workoutRepository.get(0)
                    val exercisesSaturday = workoutRepository.get(0)

                    if(controller == MyConstants.CONTROLLER.CONTROLLER_FALSE) {
                        week = Week(
                            id, name, description,
                            exercisesSunday,
                            exercisesMonday,
                            exercisesTuesday,
                            exercisesWednesday,
                            exercisesThursday,
                            exercisesFriday,
                            exercisesSaturday,
                            controller
                        )

                        list.add(0, week)
                    }
                }
            }
            cursor.close()

        }catch (e: Exception){
            return list
        }
        return list
    }

}