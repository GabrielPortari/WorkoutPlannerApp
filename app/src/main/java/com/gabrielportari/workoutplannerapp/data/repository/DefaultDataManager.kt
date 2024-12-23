package com.gabrielportari.workoutplannerapp.data.repository

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants

class DefaultDataManager{

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
    }

    private fun createDefaultWorkouts(db: SQLiteDatabase?){
        /*CRIAÇÃO DE TREINOS PADRÃO (ABC - PUSH PULL LEGS) */
        val workoutValues = ContentValues()
        workoutValues.put(MyConstants.DATABASE.WORKOUT_COLUMNS.ID, 1)
        workoutValues.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, "Treino A")
        workoutValues.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, "Treino com foco em peitoral, ombro e triceps.")
        workoutValues.put(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val workoutValues2 = ContentValues()
        workoutValues2.put(MyConstants.DATABASE.WORKOUT_COLUMNS.ID, 2)
        workoutValues2.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, "Treino B")
        workoutValues2.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, "Treino com foco em costas, biceps e abdomem.")
        workoutValues2.put(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val workoutValues3 = ContentValues()
        workoutValues3.put(MyConstants.DATABASE.WORKOUT_COLUMNS.ID, 3)
        workoutValues3.put(MyConstants.DATABASE.WORKOUT_COLUMNS.NAME, "Treino C")
        workoutValues3.put(MyConstants.DATABASE.WORKOUT_COLUMNS.DESCRIPTION, "Treino com foco em pernas e panturrilhas.")
        workoutValues3.put(MyConstants.DATABASE.WORKOUT_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        db?.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, workoutValues)
        db?.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, workoutValues2)
        db?.insert(MyConstants.DATABASE.WORKOUT_TABLE_NAME, null, workoutValues3)

    }

    private fun createDefaultChestExercises(db: SQLiteDatabase?){
        val exerciseValues1 = ContentValues()
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 1)
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Supino Inclinado")
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco em superior de peito, descansar de 45-90 segundos.")
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "4 series, 1-3 reps")
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues2 = ContentValues()
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 1)
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Supino Reto")
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco em peito completo, descansar de 45-90 segundos.")
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "6 series, 10-15 reps")
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues3 = ContentValues()
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 1)
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Tríceps Corda")
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco em triceps, descansar de 45-90 segundos.")
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "4 series, 8-10 reps")
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues4 = ContentValues()
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 1)
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Elevação lateral")
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco no deltoide lateral, descansar de 45-90 segundos.")
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "4 series, 12-15 reps")
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues1)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues2)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues3)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues4)

    }

    private fun createDefaultBackExercises(db: SQLiteDatabase?){
        val exerciseValues1 = ContentValues()
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 2)
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Puxada Alta")
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco nas dorsais, descansar de 45-90 segundos.")
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "6 series, 10-15 reps")
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues2 = ContentValues()
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 2)
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Remada Baixa")
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco no miolo das costas, descansar de 45-90 segundos.")
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "6 series, 6-10 reps")
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues3 = ContentValues()
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 2)
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Rosca Martelo")
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco em biceps braquial, descansar de 45-90 segundos.")
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "4 series, 8-10 reps")
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues4 = ContentValues()
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 2)
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Crucifixo Invertido")
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco em deltoide posterior, descansar de 45-90 segundos.")
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "4 series, 12-15 reps")
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues1)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues2)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues3)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues4)
    }

    private fun createDefaultLegsExercises(db: SQLiteDatabase?){
        val exerciseValues1 = ContentValues()
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 3)
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Agachamento Livre")
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco no quadriceps e glúteo, descansar de 90-120 segundos.")
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "8 series, 6-8 reps")
        exerciseValues1.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues2 = ContentValues()
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID,3)
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Stiff")
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco no posterior de coxas, descansar de 45-90 segundos.")
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "6 series, 10-12 reps")
        exerciseValues2.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues3 = ContentValues()
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 3)
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Leg Press")
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco em quadríceps, descansar de 60-90 segundos.")
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "4 series, 8-10 reps")
        exerciseValues3.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        val exerciseValues4 = ContentValues()
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.WORKOUT_ID, 3)
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.NAME, "Panturrilha Sentado")
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.DESCRIPTION, "Foco em panturrilha, descansar de 45-90 segundos.")
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.REP_COUNT, "4 series, 15-20 reps")
        exerciseValues4.put(MyConstants.DATABASE.EXERCISE_COLUMNS.CONTROLLER, MyConstants.CONTROLLER.CONTROLLER_FALSE)

        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues1)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues2)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues3)
        db?.insert(MyConstants.DATABASE.EXERCISE_TABLE_NAME, null, exerciseValues4)
    }

    private fun createDefaultWeek(db: SQLiteDatabase?){
        val weekValues = ContentValues()
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.ID, 1)
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.NAME, "Semana ABC Padrão")
        weekValues.put(MyConstants.DATABASE.WEEK_COLUMNS.DESCRIPTION, "Treino ABC")
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
        userValues.put(MyConstants.DATABASE.USER_COLUMNS.NAME, "Usuário")
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