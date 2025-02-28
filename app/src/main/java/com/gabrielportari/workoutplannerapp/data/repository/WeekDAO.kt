package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.content.Context
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.data.repository.dao.IWeekDAO

class WeekDAO private constructor(context: Context) : IWeekDAO {

    private val database = PlannerDatabase(context)
    val workoutDAO = WorkoutDAO.getInstance(context)

    companion object{
        private lateinit var repository: WeekDAO
        fun getInstance(context: Context): WeekDAO{
            if(!Companion::repository.isInitialized){
                repository = WeekDAO(context)
            }
            return repository
        }
    }

    override fun insert(week: Week) : Boolean{
        val db = database.writableDatabase
        return try{

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
        }finally {
            db.close()
        }
    }

    override fun update(week: Week) : Boolean{
        val db = database.writableDatabase

        return try {
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
        }finally {
            db.close()
        }
    }

    override fun workoutDeleted(id: Int) : Boolean{
        val db = database.writableDatabase

        return try {
            val values = ContentValues().apply {
                put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY, 0)
                put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY, 0)
                put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY, 0)
                put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY, 0)
                put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY, 0)
                put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY, 0)
                put(MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY, 0)
            }

            val selection = MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SUNDAY + " = ? OR " +
                    MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_MONDAY + " = ? OR " +
                    MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_TUESDAY + " = ? OR " +
                    MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_WEDNESDAY + " = ? OR " +
                    MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_THURSDAY + " = ? OR " +
                    MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_FRIDAY + " = ? OR " +
                    MyConstants.DATABASE.WEEK_COLUMNS.WEEK_WORKOUT_ID_DAY_SATURDAY + " = ?"

            val args = arrayOf(
                id.toString(),
                id.toString(),
                id.toString(),
                id.toString(),
                id.toString(),
                id.toString(),
                id.toString()
            )

            db.update(MyConstants.DATABASE.WEEK_TABLE_NAME, values, selection, args)

            true
        }catch (e: Exception){
            false
        }finally {
            db.close()
        }
    }

    override fun insertWorkoutDay(day: String, weekId: Int, id: Int): Boolean{
        val db = database.writableDatabase

        return try{
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
        }finally {
            db.close()
        }
    }

    override fun deleteWorkoutDay(week: Week, day: String) : Boolean{
        val db = database.writableDatabase

        return try {
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
        }finally {
            db.close()
        }
    }

    override fun delete(id: Int) : Boolean{
        val db = database.writableDatabase

        return try{
            val selection = MyConstants.DATABASE.WEEK_COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(MyConstants.DATABASE.WEEK_TABLE_NAME, selection, args)

            true
        }catch (e: Exception){
            false
        }finally {
            db.close()
        }
    }

    override fun get(id: Int) : Week?{
        var week: Week? = null
        val db = database.readableDatabase

        try {
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
                    val exercisesSunday = workoutDAO.get(workoutIdSunday)
                    val exercisesMonday = workoutDAO.get(workoutIdMonday)
                    val exercisesTuesday = workoutDAO.get(workoutIdTuesday)
                    val exercisesWednesday = workoutDAO.get(workoutIdWednesday)
                    val exercisesThursday = workoutDAO.get(workoutIdThursday)
                    val exercisesFriday = workoutDAO.get(workoutIdFriday)
                    val exercisesSaturday = workoutDAO.get(workoutIdSaturday)

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
        }finally {
            db.close()
        }
        return week
    }

    override fun getAll() : List<Week>{
        var week: Week? = null
        val list = mutableListOf<Week>()
        val db = database.readableDatabase
        try {

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
                    val exercisesSunday = workoutDAO.get(workoutIdSunday)
                    val exercisesMonday = workoutDAO.get(workoutIdMonday)
                    val exercisesTuesday = workoutDAO.get(workoutIdTuesday)
                    val exercisesWednesday = workoutDAO.get(workoutIdWednesday)
                    val exercisesThursday = workoutDAO.get(workoutIdThursday)
                    val exercisesFriday = workoutDAO.get(workoutIdFriday)
                    val exercisesSaturday = workoutDAO.get(workoutIdSaturday)

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
        }finally {
            db.close()
        }
        return list
    }

    override fun getAllExceptController() : List<Week>{
        var week: Week? = null
        val list = mutableListOf<Week>()
        val db = database.readableDatabase

        try {

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
                    val exercisesSunday = workoutDAO.get(0)
                    val exercisesMonday = workoutDAO.get(0)
                    val exercisesTuesday = workoutDAO.get(0)
                    val exercisesWednesday = workoutDAO.get(0)
                    val exercisesThursday = workoutDAO.get(0)
                    val exercisesFriday = workoutDAO.get(0)
                    val exercisesSaturday = workoutDAO.get(0)

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
        }finally {
            db.close()
        }

        return list
    }

}