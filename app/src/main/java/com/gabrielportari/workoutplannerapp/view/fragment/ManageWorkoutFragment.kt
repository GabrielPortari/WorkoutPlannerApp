package com.gabrielportari.workoutplannerapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.databinding.FragmentManageWorkoutBinding
import com.gabrielportari.workoutplannerapp.service.helper.EmptyDataObserver
import com.gabrielportari.workoutplannerapp.service.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.service.model.Workout
import com.gabrielportari.workoutplannerapp.service.repository.Data
import com.gabrielportari.workoutplannerapp.view.adapter.WorkoutAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.ManageWorkoutViewModel


class ManageWorkoutFragment : Fragment() {

    private lateinit var viewModel: ManageWorkoutViewModel
    private var _binding: FragmentManageWorkoutBinding? = null
    private val binding get() = _binding!!
    val adapter = WorkoutAdapter()

    val workouts = Data.workouts

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //viewModel = ViewModelProvider(this).get(ManageWorkoutViewModel::class.java)
        _binding = FragmentManageWorkoutBinding.inflate(inflater, container, false)

        binding.recyclerWorkouts.layoutManager = LinearLayoutManager(context)
        binding.recyclerWorkouts.adapter = adapter
        adapter.updateWorkouts(workouts)

        val listener = object: WorkoutListener {
            override fun onNewClick(id: Int) {
                TODO("Not yet implemented")
            }

            override fun onDeleteClick(id: Int) {
                TODO("Not yet implemented")
            }

            override fun onEditClick(id: Int) {
                TODO("Not yet implemented")
            }

        }

        adapter.attachListener(listener)

        val emptyDataObserver = EmptyDataObserver(binding.recyclerWorkouts, view?.findViewById(R.id.empty_data_parent))
        adapter.registerAdapterDataObserver(emptyDataObserver)

        return binding.root
    }

    private fun observe(){
        viewModel.workoutList.observe(viewLifecycleOwner){
            adapter.updateWorkouts(it)
        }

        view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.listWorkouts()
    }

}