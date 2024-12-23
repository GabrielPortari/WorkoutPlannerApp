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

    private val defaultDataManager = DefaultDataManager()

    override fun onCreate(db: SQLiteDatabase?) {

        /* CRIAÇÃO DAS TABELAS */
        defaultDataManager.createTables(db)

        /* INSERÇÃO DOS BOTÕES DE ADICIONAR TREINO E SEMANA */
        defaultDataManager.createButtons(db)

        /*INSERÇÃO DOS DADOS PADRÃO*/
        defaultDataManager.createDefaultData(db)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}