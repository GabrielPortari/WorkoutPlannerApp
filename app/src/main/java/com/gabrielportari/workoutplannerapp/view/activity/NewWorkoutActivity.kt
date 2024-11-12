package com.gabrielportari.workoutplannerapp.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.databinding.ActivityNewWorkoutBinding
import com.gabrielportari.workoutplannerapp.viewmodel.ManageWorkoutViewModel

class NewWorkoutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewWorkoutBinding
    private lateinit var viewModel: ManageWorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_workout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityNewWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var viewModel = ViewModelProvider(this).get(ManageWorkoutViewModel::class.java)


        /* eventos */
        binding.buttonNewExercise.setOnClickListener{

        }
        binding.buttonEndWorkout.setOnClickListener{
            handleSave()
        }
    }

    private fun handleSave(){
        val workout = Workout(
        10,
            binding.textInputWorkoutName.text.toString(),
            binding.textInputWorkoutDescription.text.toString(),
            emptyList()
        )
    }
}