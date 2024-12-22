package com.gabrielportari.workoutplannerapp.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.SelectWorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.databinding.ActivitySelectWorkoutBinding
import com.gabrielportari.workoutplannerapp.view.adapter.SelectWorkoutAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.SelectWorkoutViewModel

class SelectWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectWorkoutBinding
    private lateinit var viewModel: SelectWorkoutViewModel

    private var weekId = 0
    private var day = ""

    private val adapter = SelectWorkoutAdapter()
    private var workouts = listOf<Workout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_workout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivitySelectWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SelectWorkoutViewModel::class.java)

        binding.recyclerSelectWorkout.layoutManager = LinearLayoutManager(this)
        binding.recyclerSelectWorkout.adapter = adapter
        adapter.updateWorkouts(workouts)

        val listener = object: SelectWorkoutListener {
            override fun onSelect(id: Int) {
                viewModel.addWorkout(day, weekId, id)
                finish()
            }
        }

        adapter.attachListener(listener)
        loadData()
        observe()
    }

    private fun observe(){
        viewModel.workouts.observe(this){
            adapter.updateWorkouts(it)
        }

        viewModel.validation.observe(this) {
            if (it.status()) {
                showToast("Sucesso ao adicionar treino")
            } else {
                showToast(it.message())
            }
        }
    }

    private fun loadData(){
        val bundle = intent.extras
        if(bundle != null){
            weekId = bundle.getInt(MyConstants.KEY.ID_KEY)
            day = bundle.getString(MyConstants.KEY.DAY_KEY)!!
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onResume() {
        super.onResume()
        viewModel.list()
    }
}