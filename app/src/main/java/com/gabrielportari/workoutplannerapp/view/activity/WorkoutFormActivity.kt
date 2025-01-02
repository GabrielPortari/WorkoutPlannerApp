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
import com.gabrielportari.workoutplannerapp.databinding.ActivityWorkoutFormBinding
import com.gabrielportari.workoutplannerapp.view.adapter.ExerciseAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.WorkoutFormViewModel

class WorkoutFormActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWorkoutFormBinding
    private lateinit var viewModel: WorkoutFormViewModel

    private var workoutId = 0
    private val adapter = ExerciseAdapter()
    private var exercises = listOf<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workout_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityWorkoutFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[WorkoutFormViewModel::class.java]

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
                dialogBuilder.setTitle(R.string.delete_exercise_title)
                dialogBuilder.setMessage(R.string.delete_exercise_message)
                dialogBuilder.setPositiveButton(R.string.yes) { _, _ ->
                    viewModel.deleteExercise(id)
                }

                dialogBuilder.setNegativeButton(R.string.no) { _, _ ->

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

        observe()
        loadData()
    }

    private fun observe(){
        viewModel.validation.observe(this){
            if(it.status()){
                if(workoutId == 0){
                    showToast(R.string.success_create_workout.toString())
                }else{
                    showToast(R.string.success_edit_workout.toString())
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
                showToast(R.string.success_delete_exercise.toString())
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

                viewModel.updateWorkout(workout)

                finish()
            }else{
                showToast(R.string.fill_workout_description.toString())
            }
        }else{
            showToast(R.string.fill_workout_name.toString())
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