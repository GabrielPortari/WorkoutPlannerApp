package com.gabrielportari.workoutplannerapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielportari.workoutplannerapp.data.listener.SelectWorkoutListener
import com.gabrielportari.workoutplannerapp.databinding.FragmentHomeBinding
import com.gabrielportari.workoutplannerapp.view.adapter.HomeAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var userName = "user"

    private val adapter = HomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.recyclerHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerHome.adapter = adapter

        val listener = object: SelectWorkoutListener {
            override fun onSelect(id: Int) {
                TODO("Not yet implemented")
            }
        }

        adapter.attachListener(listener)

        viewModel.loadUserName()
        viewModel.listWorkouts()

        observe()
        return binding.root
    }

    private fun observe(){

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}