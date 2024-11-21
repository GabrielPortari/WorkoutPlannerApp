package com.gabrielportari.workoutplannerapp.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.repository.WorkoutRepository
import com.gabrielportari.workoutplannerapp.databinding.ActivityNewWorkoutBinding
import com.gabrielportari.workoutplannerapp.viewmodel.ManageWorkoutViewModel
import com.gabrielportari.workoutplannerapp.viewmodel.NewWorkoutViewModel

class NewWorkoutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewWorkoutBinding
    private lateinit var viewModel: NewWorkoutViewModel
    private var workoutId = 0
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

        observe()
        loadData()
    }

    fun observe(){
        viewModel.workout.observe(this){
            binding.textInputWorkoutName.setText(it.name)
            binding.textInputWorkoutDescription.setText(it.description)
            binding.buttonEndWorkout.text = "Editar Treino"
            binding.buttonNewExercise.visibility = android.view.View.VISIBLE
            for (exercise in it.exercises){
                //TODO: Listar exercícios do treino
            }
        }

        viewModel.workoutLoad.observe(this){
            if(!it.status()){
                showToast(it.message())
            }
        }
    }

    private fun handleSave(){
        if(!binding.textInputWorkoutName.text.isNullOrBlank()){
            if(!binding.textInputWorkoutDescription.text.isNullOrBlank()){
                val name = binding.textInputWorkoutName.text.toString()
                val description = binding.textInputWorkoutDescription.text.toString()
                val exercises = listOf<Exercise>()

                val workout = Workout(workoutId, name, description, exercises, MyConstants.CONTROLLER.CONTROLLER_FALSE)

                viewModel.createWorkout(workout)

                finish()
            }else{
                Toast.makeText(this, "Preencha a descrição do treino", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Preencha o nome do treino", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            workoutId = bundle.getInt(MyConstants.KEY.ID_KEY)
            viewModel.loadWorkout(workoutId)
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}