package com.gabrielportari.workoutplannerapp.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielportari.workoutplannerapp.R
import com.gabrielportari.workoutplannerapp.databinding.FragmentManageWorkoutBinding
import com.gabrielportari.workoutplannerapp.data.helper.EmptyDataObserver
import com.gabrielportari.workoutplannerapp.data.listener.WorkoutListener
import com.gabrielportari.workoutplannerapp.data.model.Workout
import com.gabrielportari.workoutplannerapp.view.activity.NewWorkoutActivity
import com.gabrielportari.workoutplannerapp.view.adapter.WorkoutAdapter
import com.gabrielportari.workoutplannerapp.viewmodel.ManageWorkoutViewModel


class ManageWorkoutFragment : Fragment() {

    private lateinit var viewModel: ManageWorkoutViewModel
    private var _binding: FragmentManageWorkoutBinding? = null
    private val binding get() = _binding!!

    private val adapter = WorkoutAdapter()
    private var workouts = listOf<Workout>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ManageWorkoutViewModel::class.java)
        _binding = FragmentManageWorkoutBinding.inflate(inflater, container, false)

        binding.recyclerWorkouts.layoutManager = LinearLayoutManager(context)
        binding.recyclerWorkouts.adapter = adapter
        adapter.updateWorkouts(workouts)

        val listener = object: WorkoutListener {
            override fun onNewClick() {
                val intent = Intent(context, NewWorkoutActivity::class.java)
                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
                viewModel.deleteWorkout(id)
            }

            override fun onEditClick(workout: Workout) {
                viewModel.editWorkout(workout)
            }
        }

        adapter.attachListener(listener)

        val emptyDataObserver = EmptyDataObserver(binding.recyclerWorkouts, view?.findViewById(R.id.empty_data_parent))
        adapter.registerAdapterDataObserver(emptyDataObserver)

        observe()

        return binding.root
    }

    private fun observe(){
        viewModel.workoutList.observe(viewLifecycleOwner){
            adapter.updateWorkouts(it)
        }

        viewModel.validation.observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
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