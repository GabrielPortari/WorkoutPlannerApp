package com.gabrielportari.workoutplannerapp.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.databinding.ActivitySelectWorkoutBinding
import com.gabrielportari.workoutplannerapp.viewmodel.SelectWorkoutViewModel

class SelectWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectWorkoutBinding
    private lateinit var viewModel: SelectWorkoutViewModel

    private var weekId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_workout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}