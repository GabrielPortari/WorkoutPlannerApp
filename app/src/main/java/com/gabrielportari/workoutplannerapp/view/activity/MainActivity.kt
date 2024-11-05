package com.gabrielportari.workoutplannerapp.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.databinding.ActivityMainBinding
import com.gabrielportari.workoutplannerapp.view.fragment.HomeFragment
import com.gabrielportari.workoutplannerapp.view.fragment.ManageWeekFragment
import com.gabrielportari.workoutplannerapp.view.fragment.ManageWorkoutFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Configuração do bottom navigation */
        val fragmentHome = HomeFragment()
        val fragmentManageWorkouts = ManageWorkoutFragment()
        val fragmentManageWeek = ManageWeekFragment()

        /* Define o fragment home como inicial */
        changeBottomNavFragment(fragmentHome)

        /* Switch para escolher o fragment */
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.bottom_menu_home -> changeBottomNavFragment(fragmentHome)
                R.id.bottom_menu_manage_workouts -> changeBottomNavFragment(fragmentManageWorkouts)
                R.id.bottom_menu_manage_week -> changeBottomNavFragment(fragmentManageWeek)
            }
            true
        }
    }

    /* Função para mudar o fragment */
    private fun changeBottomNavFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragmentContainerView, fragment)
        commit()
    }
}