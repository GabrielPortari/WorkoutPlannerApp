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
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.ExerciseListener
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.databinding.ActivityNewWorkoutBinding
import com.gabrielportari.workoutplannerapp.view.adapter.ExerciseAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.NewWorkoutViewModel

class NewWorkoutFormActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewWorkoutBinding
    private lateinit var viewModel: NewWorkoutViewModel

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
        viewModel = ViewModelProvider(this).get(NewWorkoutViewModel::class.java)

        adapter.updateExercises(exercises)

        val listener = object: ExerciseListener {
            override fun onNewClick() {
                val intent = Intent(applicationContext, NewExerciseFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(MyConstants.KEY.WORKOUT_ID_KEY, workoutId)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(applicationContext)
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
                val intent = Intent(applicationContext, NewExerciseFormActivity::class.java)
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

        loadData()
        observe()
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
            TODO("OS DADOS NÃO ESTÃO SENDO CARREGADOS CORRETAMENTE")
            binding.textInputLayoutName.editText?.setText(it.name)
            binding.textInputLayoutDescription.editText?.setText(it.description)
        }

        viewModel.workoutLoad.observe(this){
            if(!it.status()){
                showToast(it.message())
            }
        }

        viewModel.exerciseList.observe(this){
            TODO("A LISTA NÃO ESTÁ SENDO CARREGADA CORRETAMENTE")
            adapter.updateExercises(it)
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
            viewModel.listExercises(workoutId)

            binding.arraylistExercises.visibility = android.view.View.VISIBLE
            binding.textEmptyList.visibility = android.view.View.GONE

        }else{
            binding.arraylistExercises.visibility = android.view.View.GONE
            binding.textEmptyList.visibility = android.view.View.VISIBLE
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}