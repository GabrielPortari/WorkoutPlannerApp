package com.gabrielportari.workoutplannerapp.view.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.model.Exercise
import com.gabrielportari.workoutplannerapp.databinding.ActivityExerciseFormBinding
import com.gabrielportari.workoutplannerapp.viewmodel.ExerciseFormViewModel

class ExerciseFormActivity : AppCompatActivity() {

    private lateinit var binding : ActivityExerciseFormBinding
    private lateinit var viewModel: ExerciseFormViewModel
    private var repCountSelected: String = ""
    private var exerciseId = 0
    private var workoutId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exercise_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityExerciseFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(ExerciseFormViewModel::class.java)
        loadSpinner()

        binding.spinnerRepCount.setSelection(0)
        binding.spinnerRepCount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                repCountSelected = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Nothing to do here")
            }

        }

        binding.buttonSave.setOnClickListener{
            handleSave()
        }

        loadData()
        observe()
    }

    private fun observe(){
        viewModel.validation.observe(this){
            if(it.status()){
                if(exerciseId == 0){
                    showToast("Exercicio criado com sucesso")
                }else{
                    showToast("Exercicio editado com sucesso")
                }
            }else{
                showToast(it.message())
            }
        }

        viewModel.exercise.observe(this){
            binding.textInputExerciseName.setText(it.name)
            binding.textInputExerciseDescription.setText(it.description)
        }

        viewModel.exerciseLoad.observe(this){
            if(!it.status()){
                showToast(it.message())
            }
        }

    }


    private fun handleSave(){
        if(!binding.textInputExerciseName.text.isNullOrBlank()){
            if(!binding.textInputExerciseDescription.text.isNullOrBlank()){
                val name = binding.textInputExerciseName.text.toString()
                val description = binding.textInputExerciseDescription.text.toString()
                val exercise = Exercise(exerciseId, workoutId, name, description, repCountSelected, MyConstants.CONTROLLER.CONTROLLER_FALSE)

                if(exerciseId == 0) {
                    viewModel.createExercise(exercise)
                }else{
                    viewModel.updateExercise(exercise)
                }

            }else{
                Toast.makeText(this, "Preencha a descrição do exercicio", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Preencha o nome do exercicio", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun loadData(){
        val bundle = intent.extras
        if(bundle != null){
            when{
                bundle.containsKey(MyConstants.KEY.WORKOUT_ID_KEY) -> {
                    workoutId = bundle.getInt(MyConstants.KEY.WORKOUT_ID_KEY)
                }
                bundle.containsKey(MyConstants.KEY.ID_KEY) -> {
                    exerciseId = bundle.getInt(MyConstants.KEY.ID_KEY)
                    viewModel.loadExercise(exerciseId)
                }
            }
        }
    }

    private fun loadSpinner(){
        binding.spinnerRepCount
        val repRanges = arrayOf("1-2 reps.", "2-4 reps.", "4-6 reps.", "6-8 reps.", "8-12 reps", "10-15 reps", "15-20 reps")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, repRanges)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRepCount.adapter = adapter
    }

    fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}