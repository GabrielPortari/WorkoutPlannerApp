package com.gabrielportari.workoutplannerapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielportari.workoutplannerapp.data.constants.MyConstants
import com.gabrielportari.workoutplannerapp.data.listener.SelectWorkoutListener
import com.gabrielportari.workoutplannerapp.databinding.FragmentHomeBinding
import com.gabrielportari.workoutplannerapp.view.adapter.HomeAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var userName = "user"
    private var weekIds = 0


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

        binding.imageNameChange.setOnClickListener{
            TODO()
        }

        adapter.attachListener(listener)

        viewModel.loadUserName(MyConstants.USER_ID.ID)
        viewModel.loadWeek(weekIds)

        observe()
        return binding.root
    }

    private fun observe(){
        viewModel.userName.observe(viewLifecycleOwner){
            userName = it
            binding.textHelloUser.text = "Olá $userName,"
        }
        viewModel.weekName.observe(viewLifecycleOwner){
            binding.textHomeWeekName.text = it
        }

        viewModel.workouts.observe(viewLifecycleOwner) {
            adapter.updateWorkouts(it)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUserName(MyConstants.USER_ID.ID)
        viewModel.loadWeek(weekIds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}