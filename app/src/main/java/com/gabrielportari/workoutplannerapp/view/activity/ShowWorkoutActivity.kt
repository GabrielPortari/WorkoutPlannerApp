package com.gabrielportari.workoutplannerapp.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.databinding.ActivityShowWorkoutBinding
import com.gabrielportari.workoutplannerapp.view.adapter.ExerciseShowAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.ShowWorkoutViewModel

class ShowWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowWorkoutBinding
    private lateinit var viewModel: ShowWorkoutViewModel

    private var workoutId = 0

    private val adapter = ExerciseShowAdapter()
    private var exercises = listOf<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_workout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityShowWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ShowWorkoutViewModel::class.java]

        binding.recyclerShowExercise.layoutManager = LinearLayoutManager(this)
        binding.recyclerShowExercise.adapter = adapter
        adapter.updateExercises(exercises)

        observe()
        loadData()
    }

    private fun observe(){
        viewModel.workout.observe(this){
            binding.textShowWorkoutName.text = it.name
            binding.textShowWorkoutDescription.text = it.description
        }
        viewModel.exerciseList.observe(this){
            adapter.updateExercises(it)
        }
    }

    private fun loadData(){
        val bundle = intent.extras
        if(bundle != null){
            workoutId = bundle.getInt(MyConstants.KEY.WORKOUT_ID_KEY)
            viewModel.loadWorkout(workoutId)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.listExercises(workoutId)
    }

}