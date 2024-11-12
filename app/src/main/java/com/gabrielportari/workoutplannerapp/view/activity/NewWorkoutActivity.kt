package com.gabrielportari.workoutplannerapp.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository
import com.gabrielportari.workoutplannerapp.databinding.ActivityNewWorkoutBinding
import com.gabrielportari.workoutplannerapp.viewmodel.ManageWorkoutViewModel
import com.gabrielportari.workoutplannerapp.viewmodel.NewWorkoutViewModel

class NewWorkoutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewWorkoutBinding
    private lateinit var viewModel: NewWorkoutViewModel

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

        viewModel = ViewModelProvider(this).get(NewWorkoutViewModel::class.java)


        /* eventos */
        binding.buttonNewExercise.setOnClickListener{

        }
        binding.buttonEndWorkout.setOnClickListener{
            handleSave()
        }
    }

    fun observe(){
        viewModel.create.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

    }

    private fun handleSave(){
        val wr = WorkoutRepository();

        val workout = Workout(
            wr.getAll().size+2,
            binding.textInputWorkoutName.text.toString(),
            binding.textInputWorkoutDescription.text.toString(),
            emptyList()
        )

        viewModel.createWorkout(workout)

        finish()
    }
}