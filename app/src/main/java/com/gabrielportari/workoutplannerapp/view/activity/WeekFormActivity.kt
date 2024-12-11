package com.gabrielportari.workoutplannerapp.view.activity

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
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.data.model.WorkoutDay
import com.gabrielportari.workoutplannerapp.databinding.ActivityWeekFormBinding
import com.gabrielportari.workoutplannerapp.view.adapter.WorkoutDayAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.WeekFormViewModel

class WeekFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeekFormBinding
    private lateinit var viewModel: WeekFormViewModel

    private var weekId = 0
    private val adapter = WorkoutDayAdapter()
    private var workoutDays = listOf<WorkoutDay>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_week_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityWeekFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(WeekFormViewModel::class.java)

        binding.recyclerWorkoutDay.layoutManager = LinearLayoutManager(this)
        binding.recyclerWorkoutDay.adapter = adapter
        adapter.updateWorkouts(workoutDays)

        val listener = object: WorkoutListener {
            override fun onNewClick() {
                TODO("Not yet implemented")
            }

            override fun onDeleteClick(id: Int) {
                TODO("Not yet implemented")
            }

            override fun onEditClick(workout: Workout) {
                TODO("Not yet implemented")
            }
        }

        adapter.attachListener(listener)

        binding.buttonSaveWeek.setOnClickListener{

        }

        observe()
        loadData()
    }

    private fun observe(){
        viewModel.validation.observe(this){
            if(it.status()){
                if(weekId == 0){
                    showToast("Semana criada com sucesso")
                }else{
                    showToast("Semana editada com sucesso")
                }
            }else{
                showToast(it.message())
            }
        }

        viewModel.week.observe(this){
            binding.editWeekName.setText(it.name)
            binding.editWeekDescription.setText(it.description)
        }

        viewModel.workouts.observe(this){
            adapter.updateWorkouts(it)
        }

    }

    private fun loadData(){
        val bundle = intent.extras
        if(bundle != null){
            weekId = bundle.getInt(MyConstants.KEY.ID_KEY)
            viewModel.loadWeek(weekId)
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        if(weekId != 0){
            viewModel.loadWeek(weekId)
        }
    }
}