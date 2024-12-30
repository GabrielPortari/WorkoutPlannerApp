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
import com.gabrielportari.workoutplannerapp.data.listener.SelectWeekListener
import com.gabrielportari.workoutplannerapp.data.model.User
import com.gabrielportari.workoutplannerapp.data.model.Week
import com.gabrielportari.workoutplannerapp.databinding.ActivitySelectWeekBinding
import com.gabrielportari.workoutplannerapp.view.adapter.SelectWeekAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.SelectWeekViewModel

class SelectWeekActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectWeekBinding
    private lateinit var viewModel: SelectWeekViewModel
    private val adapter = SelectWeekAdapter()
    private var weeks = listOf<Week>()

    private var user: User = User(MyConstants.USER_ID.ID, "User", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_week)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivitySelectWeekBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SelectWeekViewModel::class.java)
        binding.recyclerSelectWeek.layoutManager = LinearLayoutManager(this)
        binding.recyclerSelectWeek.adapter = adapter
        adapter.updateWeeks(weeks)

        val listener = object: SelectWeekListener {
            override fun onSelect(id: Int) {
                viewModel.selectWeek(id)
                finish()
            }
        }

        adapter.attachListener(listener)
        viewModel.loadUser()
        viewModel.list()
        observe()
    }

    private fun observe(){
        viewModel.weeks.observe(this){
            adapter.updateWeeks(it)
        }

        viewModel.user.observe(this){
            user = it
        }

        viewModel.validation.observe(this) {
            if (it.status()) {
                showToast("Sucesso ao adicionar semana")
            } else {
                showToast(it.message())
            }
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