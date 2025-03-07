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
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutDayListener
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
        viewModel = ViewModelProvider(this)[WeekFormViewModel::class.java]

        binding.recyclerWorkoutDay.layoutManager = LinearLayoutManager(this)
        binding.recyclerWorkoutDay.adapter = adapter
        adapter.updateWorkouts(workoutDays)

        val listener = object: WorkoutDayListener {
            override fun onNewClick(day: String) {
                val intent = Intent(applicationContext, SelectWorkoutActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(MyConstants.KEY.ID_KEY, weekId)
                bundle.putString(MyConstants.KEY.DAY_KEY, day)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(day: String, id: Int) {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@WeekFormActivity)
                dialogBuilder.setTitle(R.string.delete_workout_title)
                dialogBuilder.setMessage(R.string.delete_workout_message)
                dialogBuilder.setPositiveButton(R.string.yes) { _, _ ->
                    viewModel.deleteWorkout(weekId, day)
                    viewModel.loadWeek(weekId)
                }
                dialogBuilder.setNegativeButton(R.string.no) { _, _ ->

                }
                val dialog = dialogBuilder.create()
                dialog.show()
            }
        }

        adapter.attachListener(listener)

        binding.buttonSaveWeek.setOnClickListener{
            handleSave()
        }

        observe()
        loadData()
    }

    private fun observe(){
        viewModel.validation.observe(this){
            if(it.status()){
                showToast(resources.getString(R.string.success_edit_week))
            }else{
                showToast(it.message())
            }
        }

        viewModel.delValidation.observe(this){
            if(it.status()){
                showToast(resources.getString(R.string.success_delete_week))
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

    private fun handleSave() {
        if(!binding.editWeekName.text.isNullOrBlank()){
            if(!binding.editWeekDescription.text.isNullOrBlank()){
                val name = binding.editWeekName.text.toString().trim().replace("\n", "")
                val description = binding.editWeekDescription.text.toString().trim().replace("\n", " ")
                viewModel.update(name, description, weekId)
                finish()
            }else{
                showToast(resources.getString(R.string.fill_week_description))
            }
        }else{
            showToast(resources.getString(R.string.fill_week_name))

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