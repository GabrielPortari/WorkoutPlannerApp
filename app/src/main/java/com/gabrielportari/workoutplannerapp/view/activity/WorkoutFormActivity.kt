package com.gabrielportari.workoutplannerapp.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.ExerciseListener
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.databinding.ActivityNewWorkoutBinding
import com.gabrielportari.workoutplannerapp.view.adapter.ExerciseAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.WorkoutFormViewModel

class WorkoutFormActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewWorkoutBinding
    private lateinit var viewModel: WorkoutFormViewModel

    private var workoutId = 0
    private val adapter = ExerciseAdapter()
    private var exercises = listOf<Exercise>()

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
        viewModel = ViewModelProvider(this).get(WorkoutFormViewModel::class.java)

        binding.recyclerViewExercises.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewExercises.adapter = adapter
        adapter.updateExercises(exercises)

        val listener = object: ExerciseListener {
            override fun onNewClick() {
                val intent = Intent(applicationContext, ExerciseFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(MyConstants.KEY.WORKOUT_ID_KEY, workoutId)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@WorkoutFormActivity)
                dialogBuilder.setTitle("Excluir exercicio")
                dialogBuilder.setMessage("Tem certeza que deseja excluir esse exercicio?")
                dialogBuilder.setPositiveButton("Sim") { _, _ ->
                    viewModel.deleteExercise(id)
                }

                dialogBuilder.setNegativeButton("Nao") { _, _ ->

                }
                val dialog = dialogBuilder.create()
                dialog.show()
            }

            override fun onEditClick(exercise: Exercise) {
                val intent = Intent(applicationContext, ExerciseFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(MyConstants.KEY.ID_KEY, exercise.id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        adapter.attachListener(listener)

        binding.buttonEndWorkout.setOnClickListener{
            handleSave()
        }

        if(workoutId != 0){
            viewModel.listExercises(workoutId)
        }

        observe()
        loadData()
    }

    fun observe(){
        viewModel.validation.observe(this){
            if(it.status()){
                if(workoutId == 0){
                    showToast("Treino criado com sucesso")
                }else{
                    showToast("Treino editado com sucesso")
                }
            }else{
                showToast(it.message())
            }
        }

        viewModel.workout.observe(this){
            binding.textInputLayoutName.editText?.setText(it.name)
            binding.textInputLayoutDescription.editText?.setText(it.description)
        }

        viewModel.exerciseList.observe(this){
            adapter.updateExercises(it)
        }

        viewModel.deleteExercise.observe(this){
            if(it.status()){
                viewModel.listExercises(workoutId)
                showToast("Exercicio deletado com sucesso")
            }else{
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

                if(workoutId == 0) {
                    viewModel.createWorkout(workout)
                }else{
                    viewModel.updateWorkout(workout)
                }

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

            binding.recyclerViewExercises.visibility = android.view.View.VISIBLE
            binding.textEmptyList.visibility = android.view.View.GONE

        }else{
            binding.recyclerViewExercises.visibility = android.view.View.GONE
            binding.textEmptyList.visibility = android.view.View.VISIBLE
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        if(workoutId != 0){
            viewModel.listExercises(workoutId)
        }
    }
}