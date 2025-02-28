package com.gabrielportari.workoutplannerapp.view.activity

import android.icu.lang.UCharacter.toLowerCase
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
        viewModel = ViewModelProvider(this)[ExerciseFormViewModel::class.java]
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

        binding.editSetsCount.setOnClickListener{
            binding.editSetsCount.text.clear()
        }

        loadData()
        observe()
    }

    private fun observe(){
        viewModel.validation.observe(this){
            if(it.status()){
                if(exerciseId == 0){
                    showToast(resources.getString(R.string.success_create_exercise))
                }else{
                    showToast(resources.getString(R.string.success_edit_exercise))
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
            if (!binding.editSetsCount.text.isNullOrBlank()) {

                val name = binding.textInputExerciseName.text.toString().trim().replace("\n", "")

                val description: String = if(binding.textInputExerciseDescription.text.isNullOrBlank()){
                    ""
                }else{
                    binding.textInputExerciseDescription.text.toString().trim().replace("\n", "")
                }

                val sets = binding.editSetsCount.text.toString()
                val exerciseCount = "$sets " + toLowerCase(getString(R.string.sets)) + ", $repCountSelected"

                val exercise = Exercise(
                    exerciseId,
                    workoutId,
                    name,
                    description,
                    exerciseCount,
                    MyConstants.CONTROLLER.CONTROLLER_FALSE
                )

                if (exerciseId == 0) {
                    viewModel.createExercise(exercise)
                } else {
                    viewModel.updateExercise(exercise)
                }

                finish()

            } else {
                showToast(resources.getString(R.string.fill_sets))
            }
        }else{
            showToast(resources.getString(R.string.fill_exercise_name))
        }
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

        val repRanges = resources.getStringArray(R.array.spinner_items)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, repRanges)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRepCount.adapter = adapter
    }

    private fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}